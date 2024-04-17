package com.company.bookShop.controller;

import com.company.bookShop.service.StatsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("stats")
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/mostActiveUser")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> popularUsers(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(statsService.getActiveUser(httpServletRequest));
    }
    @GetMapping("/mostComments")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> mostComments(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(statsService.getMostComments(httpServletRequest));
    }
    @GetMapping("/books")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> popularOrder(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(statsService.getMostOrders(httpServletRequest));
    }
}
