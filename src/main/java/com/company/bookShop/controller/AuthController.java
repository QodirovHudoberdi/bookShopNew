package com.company.bookShop.controller;

import com.company.bookShop.dto.registration.RegistrationReqDto;
import com.company.bookShop.service.UserService;
import com.company.bookShop.dto.login.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.login(loginRequestDto,httpServletRequest));
    }
    @PostMapping("Registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationReqDto registrationReqDto, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.registration(registrationReqDto,httpServletRequest));
    }

}
