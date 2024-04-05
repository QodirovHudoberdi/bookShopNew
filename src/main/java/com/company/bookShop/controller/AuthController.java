package com.company.bookShop.controller;

import com.company.bookShop.dto.registration.RegistrationReqDto;
import com.company.bookShop.service.UserService;
import com.company.bookShop.dto.login.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/")
public class AuthController {

    private final UserService userService;

    @PostMapping("registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationReqDto registrationReqDto, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.registration(registrationReqDto,httpServletRequest));
    }
    @GetMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.login(loginRequestDto,httpServletRequest));
    }

}
