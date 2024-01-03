package com.example.demo.controller;

import com.example.demo.dto.message.MessageDto;
import com.example.demo.dto.response.Response;
import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserRegisterDto;
import com.example.demo.dto.user.UserUpdateDto;
import com.example.demo.service.UserService;
import com.example.demo.utils.DataTypeUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Value("${fe_production.url}")
    private  String url;

    @GetMapping("")
    public ResponseEntity<?> getUsers(@RequestParam(name="page",required = false,defaultValue = "1") Integer page,
                                      @RequestParam(name = "size", required = false, defaultValue = "10")  Integer size,
                                      @RequestParam(name = "sort", required = false, defaultValue = "ASC")  String sort){
        return userService.getUsers(page,size,sort);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated UserRegisterDto user, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        System.out.println(request.toString());
        return userService.registerUser(user,url+"notify/verified");
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id){
        return userService.getUserById(DataTypeUtils.ConvertStringToInt(id));
    }

    @GetMapping("/forgot-password/{email}")
    public void forgetPassword(@PathVariable("email")String email,HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        userService.sendEmailForgotPassword(email,url+"resetpass/confirm");
    }
    @PatchMapping("/forgot-password/reset")
    public ResponseEntity<?> resetPassword(@RequestParam("email")String email, @RequestBody UserRegisterDto user) throws MessagingException, UnsupportedEncodingException {
        userService.updatePassword(email,user.getPassword(),url+"login");
        return  new ResponseEntity<>(Response.builder().message("RESET PASSWORD SUCCESS").build(),HttpStatus.OK);
    }
    @GetMapping("/verify-mail")
    public void verifyMail(@RequestParam("token")String token) throws MessagingException, UnsupportedEncodingException {
        boolean isVerify = userService.isVerifyMail(token);
        String email=userService.getClaimFromToken(token,"e");
        if(isVerify){
            userService.sendMailWhenVerified(email,url+"login","success");
        }
        else{
            userService.sendMailWhenVerified(email,url+"login","error");
        }
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserUpdateDto userUpdateDto){
        return userService.updateUser(DataTypeUtils.ConvertStringToInt(id),userUpdateDto);
    }

}
