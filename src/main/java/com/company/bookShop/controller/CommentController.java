package com.company.bookShop.controller;

import com.company.bookShop.dto.comments.CommentsReqDto;
import com.company.bookShop.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("book/comment/")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("write")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK','WRITE_COMMENT')")
    public ResponseEntity<?> createComment(@RequestParam("bookId") Integer bookId,
                                           @RequestBody CommentsReqDto commentsReqDto,
                                           HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(commentService.createComment(bookId, commentsReqDto, httpServletRequest));
    }

    @GetMapping("getComments")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK','WRITE_COMMENT')")
    public ResponseEntity<?> createComment(@RequestParam("bookId") Integer bookId,
                                           HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(commentService.getComments(bookId, httpServletRequest));
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> updateComment(@PathVariable("id") Integer id,
                                           @RequestBody CommentsReqDto dto,
                                           HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(commentService.updateComment(id, dto, httpServletRequest));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_ALL','CREATE_BOOK')")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Integer id,
                                           HttpServletRequest httpServletRequest) {
        commentService.deleteComment(id, httpServletRequest);
        return ResponseEntity.ok().build();
    }

}
