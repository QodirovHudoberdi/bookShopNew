package com.company.bookShop.dto.comments;

import com.company.bookShop.dto.book.BookResponseDto;
import com.company.bookShop.dto.user.UserResponseDto;
import lombok.Data;

@Data
public class CommentsResDto {
    private Integer id;
    private String content;
    private BookResponseDto book;
    private UserResponseDto user;
}
