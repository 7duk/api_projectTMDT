package com.example.demo.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class UserRegisterDto {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Integer gender;
    private String phone;
    private String address;
    private String email;
    private String password;
    private String image;
}
