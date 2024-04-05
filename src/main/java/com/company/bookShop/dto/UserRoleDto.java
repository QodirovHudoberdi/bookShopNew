package com.company.bookShop.dto;

import com.company.bookShop.dto.role.RoleResDto;
import com.company.bookShop.dto.user.UserResponseDto;
import lombok.Data;

import java.util.Set;

@Data
public class UserRoleDto {
    private UserResponseDto userDto;
    private Set<RoleResDto> roleDto;
}
