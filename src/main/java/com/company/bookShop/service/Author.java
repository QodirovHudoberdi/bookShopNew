package com.company.bookShop.service;


import com.company.bookShop.dto.book.BookResponseDto;
import com.company.bookShop.dto.book.BookRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface Author {
 /*   AuthorDto create(AuthorDto authorDto);*/

    BookResponseDto addBook(BookRequestDto dto, String jwt);


    List<BookResponseDto> bookList(Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest);

    void updateBook(Integer id, BookRequestDto dto, String jwt);

    void deleteBook(Integer id, String jwt);

  /*  void updateAuthor(AuthorDto model, String jwt);*/

    void deleteAuthor(String jwt);
}