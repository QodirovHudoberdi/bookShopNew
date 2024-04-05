package com.company.bookShop.controller;

import com.company.bookShop.dto.book.BookRequestDto;
import com.company.bookShop.dto.comments.CommentsReqDto;
import com.company.bookShop.service.BookService;
import com.company.bookShop.service.CommentService;
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
    private final CommentService commentService;

    /**
     * Book Section
     */

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> createBook(@RequestBody BookRequestDto bookRequestDto,
                                        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(bookService.create(bookRequestDto, httpServletRequest));
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
        return ResponseEntity.ok(bookService.update(id, dto, httpServletRequest));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest httpServletRequest) {
        bookService.delete(id, httpServletRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * Comment Section
     */

    @PostMapping("create/comment")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK','WRITE_COMMENT')")
    public ResponseEntity<?> createComment(@RequestParam("bookId") Integer bookId,
                                           @RequestBody CommentsReqDto commentsReqDto,
                                           HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(commentService.create(bookId, commentsReqDto, httpServletRequest));
    }

    @GetMapping("getComments")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK','WRITE_COMMENT')")
    public ResponseEntity<?> createComment(@RequestParam("bookId") Integer bookId,
                                           HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(commentService.getComments(bookId, httpServletRequest));
    }

    @PutMapping("comment/update/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> updateComment(@PathVariable("id") Integer id,
                                           @RequestBody CommentsReqDto dto,
                                           HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(commentService.update(id, dto, httpServletRequest));
    }

    @DeleteMapping("comment/delete/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Integer id,
                                           HttpServletRequest httpServletRequest) {
        commentService.delete(id, httpServletRequest);
        return ResponseEntity.ok().build();
    }

}
