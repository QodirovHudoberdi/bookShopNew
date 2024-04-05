package com.company.bookShop.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}
