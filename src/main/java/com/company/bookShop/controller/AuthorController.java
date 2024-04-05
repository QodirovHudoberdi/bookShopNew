package com.company.bookShop.controller;


import com.company.bookShop.service.BookService;
import com.company.bookShop.dto.book.BookRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("author")
public class AuthorController {
//
//    private final BookService bookService;
//    //  @PostMapping("/create")
//
////    public ResponseEntity<?> create(@RequestBody USer authorDto) {
////        return ResponseEntity.ok().body(author.create(authorDto));
////    }
///*
//    @PostMapping("/add/book")
//    public ResponseEntity<?> addBook(@RequestBody BookRequestDto model) {
//        bookService.create(model, jwt);
//        return ResponseEntity.ok().build();
//    }*/
////
////    @PutMapping("/update/book/{id}")
////    public ResponseEntity<?> updateBook(@PathVariable Integer id,
////                                        @RequestBody BookRequestDto model,
////                                        @RequestHeader("Authorization") String jwt) {
////        bookService.updateBook(id, model, jwt);
////        return ResponseEntity.ok().build();
////    }
//
////    @PutMapping("/update")
////    public ResponseEntity<?> updateAuthor(@RequestBody AuthorDto model,
////                                          @RequestHeader("Authorization") String jwt) {
////        author.updateAuthor(model, jwt);
////        return ResponseEntity.ok().build();
////    }
//
//    @PutMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String jwt) {
//        bookService.deleteAuthor(jwt);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/delete/book/{id}")
//    public ResponseEntity<?> deleteBook(@PathVariable Integer id,
//                                        @RequestHeader("Authorization") String jwt) {
//        bookService.deleteBook(id, jwt);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/book/pageNo={no}/pageSize={size}")
//    public ResponseEntity<?> getBooks(@PathVariable("no") Integer no, @PathVariable("size") Integer size, HttpServletRequest httpServletRequest) {
//        return ResponseEntity.ok(bookService.bookList(no, size, httpServletRequest));
//    }
}