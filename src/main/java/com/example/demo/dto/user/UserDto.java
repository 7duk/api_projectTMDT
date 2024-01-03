package com.example.demo.dto.user;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Integer gender;
    private String phone;
    private String address;
    private String email;
    private String image;
}
