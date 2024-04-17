package com.company.bookShop.dto.book;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequestDto {
    private String name;
    private String writer;
    private Long price;
    private Integer quantity;
}