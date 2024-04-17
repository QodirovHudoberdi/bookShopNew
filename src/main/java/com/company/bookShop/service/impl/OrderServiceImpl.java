package com.company.bookShop.service.impl;

import com.company.bookShop.config.JwtTokenProvider;
import com.company.bookShop.dto.order.OrderResDto;
import com.company.bookShop.dto.orderDetails.OrderDetailsResDto;
import com.company.bookShop.entity.BooksEntity;
import com.company.bookShop.entity.Orders;
import com.company.bookShop.repository.BookRepository;
import com.company.bookShop.repository.OrderRepository;
import com.company.bookShop.service.OrderDetailsService;
import com.company.bookShop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderDetailsService orderDetailsService;

    /***
     * Confirm the Order
     * @param httpServletRequest Define to Device
     * @return Details of created Order
     */
    @Override
    @Transactional
    public OrderResDto confirmOrder(HttpServletRequest httpServletRequest) {
        int total = 0;
        List<OrderDetailsResDto> orderDetailsResDto = orderDetailsService.createOrderDetails(httpServletRequest);
        for (OrderDetailsResDto detailsResDto : orderDetailsResDto) {
            total += detailsResDto.getTotalSum();
            Optional<BooksEntity> byId = bookRepository.findById(detailsResDto.getBook().getId());
            if (byId.isPresent()) {
                BooksEntity books = byId.get();

                books.setQuantity(books.getQuantity()- detailsResDto.getQuantity());
                bookRepository.save(books);
            }
        }
        Orders orders = new Orders();
        orders.setTotal(total);

        orderRepository.save(orders);

        OrderResDto orderResDto = new OrderResDto();
        orderResDto.setId(orders.getId());
        orderResDto.setTotal(total);
        orderResDto.setOrders(orderDetailsResDto);
        return orderResDto;
    }

    /**
     * Get All Orders
     * @param httpServletRequest Define to Device
     * @return List of Orders
     */
    @Override
    public List<OrderResDto> getAllOrders(HttpServletRequest httpServletRequest) {
        List<Orders> orders = orderRepository.getAllOrderByUsername(JwtTokenProvider.getCurrentUser());
        List<OrderResDto> orderResDtoList = new ArrayList<>();
        for (Orders order : orders) {

            OrderResDto orderResDto = new OrderResDto();
            orderResDto.setId(order.getId());
            orderResDto.setOrders(orderDetailsService.getAllOrders2(httpServletRequest, order.getId()));
            orderResDto.setTotal(order.getTotal());
            orderResDtoList.add(orderResDto);
        }
        return orderResDtoList;
    }
}