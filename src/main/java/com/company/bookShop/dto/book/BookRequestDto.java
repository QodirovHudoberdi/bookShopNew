package com.company.bookShop.dto.book;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequestDto {
    private Long id;
    private String name;
    private Long authorId;
}