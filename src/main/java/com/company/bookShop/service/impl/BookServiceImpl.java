package com.company.bookShop.service.impl;


import com.company.bookShop.config.JwtTokenProvider;
import com.company.bookShop.config.NetworkDataService;
import com.company.bookShop.dto.book.BookRequestDto;
import com.company.bookShop.dto.book.BookResponseDto;
import com.company.bookShop.entity.BooksEntity;
import com.company.bookShop.exps.AlreadyExistException;
import com.company.bookShop.exps.NotFoundException;
import com.company.bookShop.exps.OkResponse;
import com.company.bookShop.mapper.BookMapping;
import com.company.bookShop.repository.BookRepository;
import com.company.bookShop.service.BookService;
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

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public final class BookServiceImpl implements BookService {
    private final static Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final NetworkDataService networkDataService;
    private final BookMapping bookMapping;

    /***
     *Create book
     * @param bookRequestDto Details of new book
     * @param httpServletRequest Define to Device
     * @return data of created book
     */
    @Override
    public BookResponseDto createBook(BookRequestDto bookRequestDto, HttpServletRequest httpServletRequest) {
        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Book Created \t\t {}", bookRequestDto);
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);

        BooksEntity books = bookRepository.findByName(bookRequestDto.getName());
        if (books==null) {
            BookResponseDto bookResponseDto=new BookResponseDto();
            bookResponseDto.setName(bookRequestDto.getName());
            bookResponseDto.setWriter(bookRequestDto.getWriter());
            bookResponseDto.setQuantity(bookRequestDto.getQuantity());
            bookResponseDto.setPrice(bookRequestDto.getPrice());
            BooksEntity entity = bookMapping.toEntity1(bookResponseDto);
            bookRepository.save(entity);
            bookResponseDto.setId(entity.getId());
            bookResponseDto.setCreatedBy(entity.getCreatedBy());
            return bookResponseDto;
        }
        LOG.error("This Book Already Added");
        throw new AlreadyExistException("This Book Added Already");
    }

    /***
     * Get Book List
     * @param pageNo Number of page
     * @param pageSize Size of page
     * @param httpServletRequest Define to Device
     * @return List of books
     */
    @Override
    public List<BookResponseDto> getList(Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Get Books \t\t {}", "");
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        Pageable page = PageRequest.of(pageNo, pageSize, Sort.by("name"));
        Page<BooksEntity> page1 = bookRepository.findAll(page);
        return bookMapping.toDto(page1);
    }

    /***
     * Update Book
     * @param id of book which we need to updateBasketByID
     * @param bookRequestDto New details of old book
     * @param httpServletRequest Define to Device
     * @return Data of updated book
     */
    @Override
    public BookResponseDto updateBook(Integer id, BookRequestDto bookRequestDto, HttpServletRequest httpServletRequest) {
        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Book Updated \t\t {}", bookRequestDto);
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        String username = JwtTokenProvider.getCurrentUser();
        Optional<BooksEntity> byId = bookRepository.findById(id);
        if (byId.isPresent()&&byId.get().getCreatedBy().equals(username)) {
            BooksEntity entity = bookMapping.updateFromDto(bookRequestDto,byId.get());
            bookRepository.save(entity);
           return bookMapping.toDto(entity);
        }
        LOG.error("Book Not Found or it isn't your book");
        throw new NotFoundException("Book Not Found or it isn't your book");
    }

    /***
     * Delete Book
     * @param id Of deleted book
     * @param httpServletRequest Define to Device
     */
    @Override
    public void deleteBook(Integer id, HttpServletRequest httpServletRequest) {
        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Book Deleted \t\t {}", "id = "+id);
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        String username = JwtTokenProvider.getCurrentUser();
        Optional<BooksEntity> byId = bookRepository.findById(id);
        if (byId.isPresent()&&byId.get().getCreatedBy().equals(username)) {
            bookRepository.delete(byId.get());
            throw new OkResponse("Successfully Deleted");
        }
        LOG.error("Book Not Found or it isn't your book");
        throw new NotFoundException("Book Not Found or it isn't your book");
    }

}