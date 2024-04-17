package com.company.bookShop.service;

import com.company.bookShop.dto.role.RoleReqDto;
import com.company.bookShop.dto.role.RoleResDto;
import com.company.bookShop.dto.user.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RoleService {
    RoleResDto createRole(RoleReqDto roleReqDto, HttpServletRequest httpServletRequest);
    List<RoleResDto> getRoles();

    void deleteRole(Long id);

    UserResponseDto getRole(Long userId, List<Long> roleId);
}
