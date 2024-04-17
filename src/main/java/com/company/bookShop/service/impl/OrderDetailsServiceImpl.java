package com.company.bookShop.service.impl;

import com.company.bookShop.config.JwtTokenProvider;
import com.company.bookShop.dto.orderDetails.OrderDetailsResDto;
import com.company.bookShop.entity.Basket;
import com.company.bookShop.entity.BooksEntity;
import com.company.bookShop.entity.OrderDetails;
import com.company.bookShop.exps.WrongException;
import com.company.bookShop.mapper.BookMapping;
import com.company.bookShop.repository.BasketRepository;
import com.company.bookShop.repository.BookRepository;
import com.company.bookShop.repository.OrderDetailsRepository;
import com.company.bookShop.service.OrderDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private final BasketRepository basketRepository;
    private final BookMapping bookMapping;
    private final BookRepository bookRepository;
    private final OrderDetailsRepository orderDetailsRepository;


    /***
     * Create new OrderDetails
     * @param httpServletRequest Define to Device
     * @return List of Order details
     */
    @Override
    public List<OrderDetailsResDto> createOrderDetails(HttpServletRequest httpServletRequest) {
        String username = JwtTokenProvider.getCurrentUser();
        List<Basket> allByCreatedBy = basketRepository.findAllByCreatedBy(username);
        List<OrderDetailsResDto> detailsResDto = new ArrayList<>();
        for (Basket basket : allByCreatedBy) {
            OrderDetailsResDto dto = new OrderDetailsResDto();
            dto.setTotalSum(basket.getTotal());
            Optional<BooksEntity> books = bookRepository.findById(basket.getBookId());
            if (books.isEmpty()) {
                throw new WrongException("Something went Wrong");
            }
            dto.setBook(bookMapping.toDto(books.get()));
            dto.setQuantity(basket.getQuantity());
            detailsResDto.add(dto);
        }
        return detailsResDto;
    }

    /***
     * Get All Orders by orderId
     * @param httpServletRequest Define to Device
     * @param id  Order Id
     * @return List of OrderDetails
     */
    @Override
    public List<OrderDetailsResDto> getAllOrders2(HttpServletRequest httpServletRequest, Integer id) {
        List<OrderDetails> byOrderId = orderDetailsRepository.findByOrderId(id);
        List<OrderDetailsResDto> orderDetailsResDto = new ArrayList<>();
        for (OrderDetails orderDetail : byOrderId) {
            Optional<BooksEntity> books = bookRepository.findById(orderDetail.getBook().getId());
            if (books.isEmpty()) {
                throw new WrongException("Something went Wrong");
            }
            BooksEntity book = books.get();
            OrderDetailsResDto orderDetailsResDto1 = new OrderDetailsResDto();
            orderDetailsResDto1.setQuantity(orderDetail.getAmount());
            orderDetailsResDto1.setBook(bookMapping.toDto(book));
            orderDetailsResDto1.setTotalSum(orderDetail.getTotalSum());
            orderDetailsResDto.add(orderDetailsResDto1);

        }
        return orderDetailsResDto;
    }
}