package com.company.bookShop.dto.role;

import com.company.bookShop.dto.PermissionDto;
import lombok.Data;

import java.util.Set;

@Data
public class RoleResDto {
    private Long id;
    private String name;
    private Set<PermissionDto> permissions;
}
