package com.company.bookShop.dto.user;

import lombok.Data;

@Data
public class UserRequestDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}
