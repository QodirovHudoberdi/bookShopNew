package com.company.bookShop.dto.orderDetails;

import com.company.bookShop.dto.basket.BasketDTO;
import com.company.bookShop.dto.book.BookResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailsResDto {
    private Integer totalSum;
    private Integer quantity;
    private BookResponseDto book;

}