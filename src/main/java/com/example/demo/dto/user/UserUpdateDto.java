package com.example.demo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Integer gender;
    private String phone;
    private String address;
    private String image;
    private String email;
}
