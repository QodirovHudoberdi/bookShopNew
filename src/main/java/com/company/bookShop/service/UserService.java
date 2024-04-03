package com.company.bookShop.service;

import com.company.bookShop.dto.login.LoginResponseDto;
import com.company.bookShop.dto.login.LoginRequestDto;
import com.company.bookShop.dto.registration.RegistrationReqDto;
import com.company.bookShop.dto.registration.RegistrationResDto;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest);

    RegistrationResDto registration(RegistrationReqDto registrationReqDto, HttpServletRequest httpServletRequest);
}
