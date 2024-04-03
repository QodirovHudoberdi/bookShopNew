package com.company.bookShop.dto.login;

import com.company.bookShop.dto.role.RoleResDto;
import lombok.Data;

import java.util.Set;

@Data
public class LoginResponseDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private RoleResDto role;
    private String token;
    private String refreshToken;
}
