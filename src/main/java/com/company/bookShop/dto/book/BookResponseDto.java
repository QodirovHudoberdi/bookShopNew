package com.company.bookShop.dto.book;

import com.company.bookShop.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Integer id;
    private String name;
    private String writer;
    private String createdBy;
    private Integer quantity;
    private Long price;
}
