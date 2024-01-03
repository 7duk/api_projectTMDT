package com.example.demo.dto.login;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@Data
public class LoginDto {
    private final String email;
    private final String password;
}
