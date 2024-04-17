package com.company.bookShop.service;

import com.company.bookShop.dto.order.OrderResDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {


    OrderResDto confirmOrder(HttpServletRequest httpServletRequest);
    List<OrderResDto> getAllOrders(HttpServletRequest httpServletRequest);
}
