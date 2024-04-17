package com.company.bookShop.service;


import com.company.bookShop.dto.book.BookResponseDto;
import com.company.bookShop.dto.book.BookRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BookService {

    BookResponseDto createBook(BookRequestDto dto, HttpServletRequest httpServletRequest);

    List<BookResponseDto> getList(Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest);

    BookResponseDto updateBook(Integer id , BookRequestDto dto, HttpServletRequest httpServletRequest);

    void deleteBook(Integer id, HttpServletRequest httpServletRequest);
}