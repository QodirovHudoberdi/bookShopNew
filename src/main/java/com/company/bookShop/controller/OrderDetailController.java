package com.company.bookShop.controller;

import com.company.bookShop.service.OrderDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("order/details/")
public class OrderDetailController {

    private final OrderDetailsService orderDetailsService;

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK','WRITE_COMMENT')")
    public ResponseEntity<?> createDetails(Integer id,HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(orderDetailsService.createOrderDetails(httpServletRequest));
    }
}
