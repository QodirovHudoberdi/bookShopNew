package com.company.bookShop.service;

import com.company.bookShop.dto.PermissionDto;
import com.company.bookShop.dto.role.RoleResDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PermissionService {
    PermissionDto createPermission(PermissionDto dto, HttpServletRequest httpServletRequest);

    RoleResDto givePermissions(Long roleId, List<Long> permissionIds);
}