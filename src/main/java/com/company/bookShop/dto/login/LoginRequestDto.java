package com.company.bookShop.dto.login;

import lombok.Data;

@Data
public class LoginRequestDto {
private String username;
private String password;
private boolean rememberMe;
}
