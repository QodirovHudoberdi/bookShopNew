package com.company.bookShop.controller;


import com.company.bookShop.dto.user.UserRequestDto;
import com.company.bookShop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable ("id") Long id,
                                    @RequestBody UserRequestDto dto, HttpServletRequest httpServletRequest) {
        userService.updateUserById(id,dto,httpServletRequest);
        return ResponseEntity.ok().build();
    }
}