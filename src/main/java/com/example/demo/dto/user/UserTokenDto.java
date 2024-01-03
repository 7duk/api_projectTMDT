package com.example.demo.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class UserTokenDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Integer gender;
    private String phone;
    private String address;
    private String email;
    private String image;
    private String roleId;
    private String token;
}
