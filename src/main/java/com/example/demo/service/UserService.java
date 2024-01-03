package com.example.demo.service;


import com.example.demo.dto.color.ColorDto;
import com.example.demo.dto.item.ItemDto;
import com.example.demo.dto.itemdetail.ItemDetailDto;
import com.example.demo.dto.response.Response;
import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserTokenDto;
import com.example.demo.dto.user.UserRegisterDto;
import com.example.demo.dto.user.UserUpdateDto;
import com.example.demo.entity.Provider;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtDecoder;
import com.example.demo.security.JwtIssuer;
import com.example.demo.utils.BcryptPassword;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    @Autowired
    private JavaMailSender mailSender;
    private final UserRepository repository;
    private final JwtIssuer issuer;
    private  final JwtDecoder decoder;
    private final ModelMapper mapper;



    public Optional<User> findUserByEmail(String email,String provider) {
        return repository.findUserByEmail(email, provider);
    }

    public ResponseEntity<?> registerUser(UserRegisterDto request,String siteURL) throws MessagingException, UnsupportedEncodingException {
        if(!repository.findUserByEmail(request.getEmail(), String.valueOf(Provider.LOCAL)).isEmpty()){
            return new ResponseEntity<>(Response.builder().message("EMAIL IS EXIST!").build(),HttpStatus.OK);
        }
        LocalDate dateUser = LocalDate.parse(request.getDateOfBirth(),DateTimeFormatter.ofPattern("yyyy-MM-dd")) ;
        System.out.println(dateUser.toString());
        Map<String,String> saltPass = BcryptPassword.encode(request.getPassword());
        String keySalt = saltPass.keySet().stream().toList().get(0);
        repository.saveUser(request.getFirstName(),
                request.getLastName(),
                dateUser,
                request.getAddress(),
                request.getImage(),
                request.getPhone(),
                request.getGender(),
                request.getEmail(),
                keySalt,
                saltPass.get(keySalt),
                1);
        Optional<User> users   = repository.findUserByEmail(request.getEmail(), String.valueOf(Provider.LOCAL));
        User user = null;
        if(!users.isEmpty()){
            user = users.get();
        }
        sendVerificationEmail( user, siteURL );
        return new ResponseEntity<>(Response.builder().message("CHECK MAIL!").build(),HttpStatus.OK);

    }
    public ResponseEntity<?> getUserById(Integer id){
        Optional<User> userOp = repository.findUserByID(id);
        if(userOp.isEmpty()){
            return new ResponseEntity<>(Response.builder().message("USER IS NOT EXIST").build(), HttpStatus.OK);
        }
        User user = userOp.get();
        UserDto userDto = UserDto.builder().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName()).dateOfBirth(user.getDateOfBirth()!=null?user.getDateOfBirth().toString():null)
                .gender(user.getGender()!=null?user.getGender():null).phone(user.getPhone()!=null?user.getPhone():null)
                .address(user.getAddress()!=null?user.getAddress():null)
                .email(user.getEmail()).image(user.getImage())
                .build();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "shopiecommerce2023@gmail.com";
        String senderName = "Admin Shopiec";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<a href=\"[[URL]]\" target=\"_parent\"><button>VERIFY</button></a>"
                + "<br>Thank you,<br>";



        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        String token = issuer.issueVerify(JwtIssuer.Request.builder().userId(user.getId())
                .email(user.getEmail())
                .roles(List.of(String.valueOf(user.getRoleId()))).build(),user.getFirstName()+" "+user.getLastName());
        content = content.replace("[[name]]", user.getFirstName()+" "+user.getLastName());
        String verifyURL = siteURL + "?token=" + token;

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
    public void sendMailWhenVerified(String email, String url,String status) throws MessagingException, UnsupportedEncodingException {
        User user = repository.findUserByEmail(email, String.valueOf(Provider.LOCAL)).get();
        String content ="";
        String incontent = (status.equalsIgnoreCase("success")==true)?("!.Please click the link "
                + "<a href=\"[[URL]]\" target=\"_self\">here</a>"+ " to continue."):".";
        if(user.getStatusAccount()==0){
            updateStatusAccount(email);
            content="Dear [[name]],<br>"+
                    "You was verified mail "+status+incontent;
            content = content.replace("[[URL]]", url);
        }
        else
        {
            content = "Dear [[name]],<br>"+"This mail has been previously verified ";
        }
        String fullName =user.getFirstName()+" "+user.getLastName();
        String toAddress = email;
        String fromAddress = "shopiecommerce2023@gmail.com";
        String senderName = "Admin Shopiec";
        String subject ="VERIFIED MAIL";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content+"<br>Thank you,<br>";
        content = content.replace("[[name]]", fullName);

        helper.setText(content, true);
        mailSender.send(message);
    }


    public boolean isVerifyMail(String token) {
        return decoder.decode(token).getClaim("isVerify").asBoolean();
    }
    public String getClaimFromToken(String token,String claim) {
        return decoder.decode(token).getClaim(claim).asString();
    }
    public void sendEmailForgotPassword(String email,String url)
            throws MessagingException, UnsupportedEncodingException {
        User user = repository.findUserByEmail(email, String.valueOf(Provider.LOCAL)).get();
        String toAddress = email;
        String fromAddress = "shopiecommerce2023@gmail.com";
        String senderName = "Admin Shopiec";
        String subject = "Reset Password";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset password:<br>"
                + "<a href=\"[[URL]]\" target=\"_self\"><button>Reset Password</button></a>"
                + "<br>Thank you,<br>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[URL]]", url+"?email="+email);
        content = content.replace("[[name]]", user.getFirstName()+" "+user.getLastName());
        helper.setText(content, true);
        mailSender.send(message);
    }
    public void updatePassword(String email,String password,String url) throws MessagingException, UnsupportedEncodingException {
        String state = "";
        User user = repository.findUserByEmail(email, String.valueOf(Provider.LOCAL)).get();
        System.out.println("password:"+password);
        System.out.println("salt:"+user.getSalt());
        String newPassword = BcryptPassword.Bpencoder.encode(user.getSalt()+password);
       if(repository.updatePassword(newPassword,email)==1){
            state =   "Reset Password Succes.Please click the link "+ "<a href=\"[[URL]]\" target=\"_self\">here</a>"+" to use application.<br>"
                    ;
       }
       else{
           state = "Reset Password Error";

       }
        String toAddress = email;
        String fromAddress = "shopiecommerce2023@gmail.com";
        String senderName = "Admin Shopiec";
        String subject = "RESET PASSWORD "+((state.equalsIgnoreCase("Reset Password Error")==true)?"ERROR":"SUCCESS");
        String content = "Dear [[name]],<br>"+state
                + "<br>Thank you,<br>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getFirstName()+" "+user.getLastName());
        content = content.replace("[[URL]]", url);
        helper.setText(content, true);
        mailSender.send(message);
    }
    public void updateStatusAccount(String email){
        repository.updateStatusAccount(1,email);
    }

    public ResponseEntity<?> getUsers(Integer page,Integer size,String sort) {
        Integer offSet = (page-1)*size;
        int count = repository.getTotalUser();
        List<User> users = repository.findUsers(size,offSet,sort);
        List<UserDto> userDtos = users.stream().map(e->{
            return mapper.map(e,UserDto.class);
        }).toList();
        return ResponseEntity.ok().header("X-Total", String.valueOf(count)).body(userDtos);
    }
    public ResponseEntity<?> updateUser(Integer id,UserUpdateDto userUpdateDto) {
        if(repository.updateUser(id,userUpdateDto.getFirstName(),userUpdateDto.getLastName(),userUpdateDto.getDateOfBirth(),
                userUpdateDto.getAddress(),userUpdateDto.getPhone(),userUpdateDto.getGender(),userUpdateDto.getEmail()
        ,userUpdateDto.getImage())==1){
            return new ResponseEntity<>(Response.builder().message("SUCCESS").build(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
