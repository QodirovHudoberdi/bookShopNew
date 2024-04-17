package com.company.bookShop.controller;

import com.company.bookShop.dto.book.BookRequestDto;
import com.company.bookShop.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("book/")
public class BookController {
    private final BookService bookService;
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> createBook(@RequestBody BookRequestDto bookRequestDto,
                                        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(bookService.createBook(bookRequestDto, httpServletRequest));
    }

    @GetMapping("getBooks")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK','WRITE_COMMENT')")
    public ResponseEntity<?> getList(@RequestParam("pageNo") Integer pageNo,
                                     @RequestParam("pageSize") Integer pageSize,
                                     HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(bookService.getList(pageNo, pageSize, httpServletRequest));
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody BookRequestDto dto,
                                    HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(bookService.updateBook(id, dto, httpServletRequest));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest httpServletRequest) {
        bookService.deleteBook(id, httpServletRequest);
        return ResponseEntity.ok().build();
    }
}