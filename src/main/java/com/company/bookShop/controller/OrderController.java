package com.company.bookShop.controller;

import com.company.bookShop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/confirm")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK','WRITE_COMMENT')")
    public ResponseEntity<?> confirmOrder(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(orderService.confirmOrder(httpServletRequest));
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK','WRITE_COMMENT')")
    public ResponseEntity<?> getAll(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(orderService.getAllOrders(httpServletRequest));
    }
}
