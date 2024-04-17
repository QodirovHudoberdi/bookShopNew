package com.company.bookShop.dto.basket;

import com.company.bookShop.dto.book.BookResponseDto;
import com.company.bookShop.dto.user.UserResponseDto;
import lombok.Data;

@Data
public class BasketResDto {
    private Integer id;
    private BookResponseDto book;
    private UserResponseDto user;
    private  Integer quantity;
    private  Integer  total ;
}
