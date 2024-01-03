package com.example.demo.service;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.dto.response.Response;
import com.example.demo.dto.user.UserTokenDto;
import com.example.demo.entity.Provider;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtDecoder;
import com.example.demo.security.JwtIssuer;
import com.example.demo.security.UserPrincipal;
import com.example.demo.utils.BcryptPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final JwtDecoder decoder;


    public ResponseEntity<?> attemptLogin(String email, String password) {
        Optional<User> optionalUser = repository.findUserByEmail(email, String.valueOf(Provider.LOCAL));
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(Response.builder().message("EMAIL IS INCORRECT").build(), HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        System.out.println("-1");
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email+" "+Provider.LOCAL, user.getSalt() + password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();
        var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                .userId(principal.getUserId())
                .email(principal.getEmail())
                .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build());
        System.out.println("TOKEN:" + token);
        UserTokenDto userDto = UserTokenDto.builder().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName()).dateOfBirth(user.getDateOfBirth().toString())
                .gender(user.getGender()).phone(user.getPhone()).address(user.getAddress())
                .email(user.getEmail()).image(user.getImage()).roleId(user.getRoleId().toString())
                .token(token).build();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    public ResponseEntity<?> attemptLoginGoogle(String authToken) {
        GoogleUserInfo userInfo = verifyGoogleToken(authToken);

        if (userInfo != null) {
            Optional<User> optionalUser = repository.findUserByEmail(userInfo.getUserEmail(), String.valueOf(Provider.GOOGLE));
            System.out.println("==>" + userInfo.getUserEmail());
            User user;
            Authentication authentication;
            if (optionalUser.isEmpty()) {
                Map<String, String> saltPass = BcryptPassword.encode(userInfo.getUserEmail() + userInfo.firstName + Provider.GOOGLE + "USER");
                String keySalt = saltPass.keySet().stream().toList().get(0);
                repository.saveUserGG(userInfo.firstName, userInfo.lastName, userInfo.urlPicture, userInfo.userEmail, keySalt, saltPass.get(keySalt), 1, 1, String.valueOf(Provider.GOOGLE));
                user = User.builder().salt(keySalt).password(userInfo.getUserEmail() + userInfo.firstName + Provider.GOOGLE + "USER").build();
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userInfo.getUserEmail()+" "+Provider.GOOGLE, user.getSalt() + user.getPassword())
                );
            } else {
                user = optionalUser.get();
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getEmail()+" "+Provider.GOOGLE, user.getSalt() + user.getEmail() + user.getFirstName() + Provider.GOOGLE + "USER"));
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var principal = (UserPrincipal) authentication.getPrincipal();
            var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userId(principal.getUserId())
                    .email(principal.getEmail())
                    .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .build());
            System.out.println("TOKEN:" + token);
            user = repository.findUserByEmail(principal.getEmail(),Provider.GOOGLE.toString()).get();
            UserTokenDto userDto = UserTokenDto.builder().id(user.getId()).firstName(user.getFirstName())
                    .lastName(user.getLastName()).dateOfBirth(user.getDateOfBirth()!=null?user.getDateOfBirth().toString():null)
                    .gender(user.getGender()!=null?user.getGender():null).phone(user.getPhone()!=null?user.getPhone():null)
                    .address(user.getAddress()!=null?user.getAddress():null)
                    .email(user.getEmail()).image(user.getImage()).roleId(user.getRoleId().toString())
                    .token(token).build();
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private static final String GOOGLE_TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token={idToken}";

    private GoogleUserInfo verifyGoogleToken(String idToken) {
        // Gửi HTTP GET request đến Google để xác thực token ID
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("idToken", idToken);
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(GOOGLE_TOKEN_INFO_URL, Map.class, uriVariables);
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && !responseBody.containsKey("error")) {
                    String userEmail = (String) responseBody.get("email");
                    String firstname = (String) responseBody.get("given_name");
                    String lastName = (String) responseBody.get("family_name");
                    String urlPicture = (String) responseBody.get("picture");
                    return new GoogleUserInfo(userEmail, firstname, lastName, urlPicture);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static class GoogleUserInfo {
        private final String userEmail;
        private final String firstName;
        private final String lastName;
        private final String urlPicture;

        public GoogleUserInfo(String userEmail, String firstName, String lastName, String urlPicture) {
            this.userEmail = userEmail;
            this.firstName = firstName;
            this.lastName = lastName;
            this.urlPicture = urlPicture;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getUrlPicture() {
            return urlPicture;
        }
    }
}
