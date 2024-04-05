package com.company.bookShop.service;

import com.company.bookShop.dto.UserRoleDto;
import com.company.bookShop.dto.role.RoleReqDto;
import com.company.bookShop.dto.role.RoleResDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RoleService {
    RoleResDto create(RoleReqDto roleReqDto, HttpServletRequest httpServletRequest);
    List<RoleResDto> getRoles();

    void deleteRole(Long id);

    UserRoleDto getRole(Long userId, List<Long> roleId);
}
