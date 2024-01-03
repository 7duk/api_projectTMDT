package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class BcryptPassword {
     public static BCryptPasswordEncoder Bpencoder = new BCryptPasswordEncoder();
    private BcryptPassword(){
    }
    public static Map<String,String> encode(String password){
        String salt = BCrypt.gensalt();
        System.out.println("salt" + salt);
        System.out.println("password" + password);
        Map<String,String> saltPass = new HashMap();
        String newPassword = Bpencoder.encode(salt+password);
        System.out.println("newPassword" + newPassword);
        saltPass.put(salt,newPassword);
        return saltPass;
    }

}
