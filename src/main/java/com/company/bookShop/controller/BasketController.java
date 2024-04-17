package com.company.bookShop.controller;

import com.company.bookShop.dto.basket.BasketReqDto;
import com.company.bookShop.service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("basket/")
@RequiredArgsConstructor
public class BasketController {
    private  final BasketService basketService;
    @PostMapping("create")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> createBaskets(@RequestBody BasketReqDto basketReqDto,
                                        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(basketService.createBaskets(basketReqDto, httpServletRequest));
    }
    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> updateBaskets(@PathVariable ("id") Integer id,
                                        @RequestBody BasketReqDto basketReqDto,
                                        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(basketService.updateBasketByID(id,basketReqDto, httpServletRequest));
    }

}
