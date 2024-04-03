package com.company.bookShop.service;


import com.company.bookShop.dto.book.BookResponseDto;
import com.company.bookShop.entity.BooksEntity;
import com.company.bookShop.mapper.BookMapping;
import com.company.bookShop.config.NetworkDataService;
import com.company.bookShop.dto.book.BookRequestDto;
import com.company.bookShop.entity.User;
import com.company.bookShop.exps.UserException;
import com.company.bookShop.repository.BookRepository;
import com.company.bookShop.repository.UserRepository;
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

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthorService implements Author {
    private final static Logger LOG = LoggerFactory.getLogger(AuthorService.class);
    private final BookRepository bookRepository;

    private final UserRepository userRepository;
    private final NetworkDataService networkDataService;


    private final BookMapping bookMapping;

//    /**
//     * Create Author
//     *
//     * @param authorDto Author fields
//     */
//
//    /**
//     * Update Author only by itself
//     *
//     * @param jwt Author  jwt  to get id
//     */
//
//
//    /**
//     * Log out Author only by himself
//     *
//     * @param jwt to get Author id
//     */


    /**
     * Check Author By Jwt
     */
    public Optional<User> getAuthor(String jwt) {
        return userRepository.findById(JwtUtil.decode(jwt));
    }

    /**
     * Create book
     *
     * @param bookRequestDto fields of new book
     * @param jwt            to identify the author
     */
    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto, String jwt) {
        Optional<User> authorById = getAuthor(jwt);
        if (authorById.isEmpty()) {
            throw new UserException("Something went wrong on JWT");
        }
        BooksEntity entity = bookMapping.toEntity(bookRequestDto);
        bookRepository.save(entity);

        return bookMapping.toDto(entity);
    }


    /**
     * Get Book List
     */
    @Override
    public List<BookResponseDto> bookList(Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        String ClientIP = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientInfo = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        Pageable page = PageRequest.of(pageNo, pageSize, Sort.by("name"));
        Page<BooksEntity> page1 = bookRepository.findAll(page);
        if (page1.hasContent()) {
            return bookMapping.toDto(page1.getContent());
        } else {
            Pageable page11 = PageRequest.of(0, 10, Sort.by("name"));
            Page<BooksEntity> pageDefault = bookRepository.findAll(page11);
            LOG.info("Get BooksList ");
            return bookMapping.toDto(pageDefault.getContent());
        }
    }

    /**
     * Update Book
     *
     * @param id             identify which book will be updated
     * @param jwt            identify who want update this book (if Authors not match progress not continue)
     * @param bookRequestDto new fields of book
     */
    @Override
    public void updateBook(Integer id, BookRequestDto bookRequestDto, String jwt) {

        Optional<User> authorById = getAuthor(jwt);

        Optional<BooksEntity> bookById = bookRepository.findById(id);
        if (authorById.isEmpty() || bookById.isEmpty()) {
            throw new UserException("Something went wrong on JWT or book id");
        }
        if (!((bookRequestDto.getAuthorId()).equals(authorById.get().getId()))) {
            throw new UserException("This Author can't update this book 😉");
        }
        BooksEntity booksEntity = bookById.get();
        String s = orElse(bookRequestDto.getName(), booksEntity.getName());
        booksEntity.setName(s);
        bookRepository.save(booksEntity);
        throw new UserException("Successfully updated book ");
    }

    /**
     * Update Book
     *
     * @param id  identify which book will be deleted
     * @param jwt identify who want to delete this book (if Authors not match progress not continue)
     */
    @Override
    public void deleteBook(Integer id, String jwt) {
        Optional<User> authorById = getAuthor(jwt);

        Optional<BooksEntity> bookById = bookRepository.findById(id);

        if (authorById.isEmpty() || bookById.isEmpty()) {
            throw new UserException("Something went wrong on JWT or book id");
        }
        BooksEntity booksEntity = bookById.get();
        if (!((booksEntity.getAuthor().getId()).equals(authorById.get().getId()))) {
            throw new UserException("This Author can't delete this book 😉");
        }

        bookRepository.delete(booksEntity);
        throw new UserException("Successfully deleted book ");
    }

    @Override
    public void deleteAuthor(String jwt) {
        Optional<User> authorById = getAuthor(jwt);


        if (authorById.isEmpty()) {
            throw new UserException("Something went wrong on JWT or book id");
        }
        userRepository.delete(authorById.get());

    }

    public String orElse(String value, String other) {
        return value != null ? value : other;
    }
}