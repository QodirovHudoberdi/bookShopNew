package com.company.bookShop.service;

import com.company.bookShop.dto.login.LoginResponseDto;
import com.company.bookShop.dto.login.LoginRequestDto;
import com.company.bookShop.dto.registration.RegistrationReqDto;
import com.company.bookShop.dto.registration.RegistrationResDto;
import com.company.bookShop.dto.user.UserRequestDto;
import com.company.bookShop.dto.user.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest);

    RegistrationResDto registration(RegistrationReqDto registrationReqDto, HttpServletRequest httpServletRequest);

    List<UserResponseDto> getUsers();

    void BlockOrUnblock(Long id, HttpServletRequest httpServletRequest);

    UserResponseDto updateUserById(Long id, UserRequestDto dto, HttpServletRequest httpServletRequest);
}

