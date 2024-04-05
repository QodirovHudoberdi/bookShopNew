package com.company.bookShop.dto.comments;

import com.company.bookShop.dto.book.BookResponseDto;
import com.company.bookShop.dto.user.UserResponseDto;
import lombok.Data;

@Data
public class CommentsDto {
    private Integer id;
    private String content;
    private UserResponseDto user;

}
