package com.company.bookShop.service;

import com.company.bookShop.dto.orderDetails.OrderDetailsResDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderDetailsService {

    List<OrderDetailsResDto> createOrderDetails(HttpServletRequest httpServletRequest);

   List<OrderDetailsResDto> getAllOrders2(HttpServletRequest httpServletRequest, Integer id);
}
