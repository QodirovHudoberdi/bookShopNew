package com.company.bookShop.service.impl;


import com.company.bookShop.config.JwtTokenProvider;
import com.company.bookShop.dto.book.BookResponseDto;
import com.company.bookShop.entity.BooksEntity;
import com.company.bookShop.exps.AlreadyExistException;
import com.company.bookShop.exps.NotFoundException;
import com.company.bookShop.exps.OkResponse;
import com.company.bookShop.mapper.BookMapping;
import com.company.bookShop.config.NetworkDataService;
import com.company.bookShop.dto.book.BookRequestDto;
import com.company.bookShop.entity.User;
import com.company.bookShop.exps.UserException;
import com.company.bookShop.mapper.UserMapping;
import com.company.bookShop.repository.BookRepository;
import com.company.bookShop.repository.UserRepository;
import com.company.bookShop.service.BookService;
import com.company.bookShop.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private final static Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;

    private final UserRepository userRepository;
    private final NetworkDataService networkDataService;


    private final BookMapping bookMapping;
    private final UserMapping userMapping;

    @Override
    public BookResponseDto create(BookRequestDto bookRequestDto, HttpServletRequest httpServletRequest) {
        BooksEntity books = bookRepository.findByName(bookRequestDto.getName());
        if (books==null) {
            BookResponseDto bookResponseDto=new BookResponseDto();
            bookResponseDto.setName(bookRequestDto.getName());
            bookResponseDto.setWriter(bookRequestDto.getWriter());
            bookResponseDto.setQuantity(bookRequestDto.getQuantity());
            BooksEntity entity = bookMapping.toEntity1(bookResponseDto);
            bookRepository.save(entity);
            bookResponseDto.setId(entity.getId());
            bookResponseDto.setCreatedBy(entity.getCreatedBy());
            return bookResponseDto;
        }
        throw new AlreadyExistException("This Book Added Already");
    }
    @Override
    public List<BookResponseDto> getList(Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        Pageable page = PageRequest.of(pageNo, pageSize, Sort.by("name"));
        Page<BooksEntity> page1 = bookRepository.findAll(page);
        return bookMapping.toDto(page1);
    }
    @Override
    public BookResponseDto update(Integer id, BookRequestDto bookRequestDto,HttpServletRequest httpServletRequest) {
        String username = JwtTokenProvider.getCurrentUser();
        Optional<BooksEntity> byId = bookRepository.findById(id);
        if (byId.isPresent()&&byId.get().getCreatedBy().equals(username)) {
            BooksEntity entity = bookMapping.updateFromDto(bookRequestDto,byId.get());
            bookRepository.save(entity);
           return bookMapping.toDto(entity);
        }
        throw new NotFoundException("Book Not Found or it isn't your book");
    }
    @Override
    public void delete(Integer id,HttpServletRequest httpServletRequest) {
        String username = JwtTokenProvider.getCurrentUser();
        Optional<BooksEntity> byId = bookRepository.findById(id);
        if (byId.isPresent()&&byId.get().getCreatedBy().equals(username)) {
            bookRepository.delete(byId.get());
            throw new OkResponse("Successfully Deleted");
        }
        throw new NotFoundException("Book Not Found or it isn't your book");
    }

}