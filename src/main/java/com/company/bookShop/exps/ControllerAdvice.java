package com.company.bookShop.exps;


import com.company.bookShop.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> on(NotFoundException e) {

        final ResponseMessage result = new ResponseMessage(HttpStatus.NOT_FOUND.value(), "Not Found",
                e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> on(AlreadyExistException e) {
        final ResponseMessage result = new ResponseMessage(HttpStatus.CONTINUE.value(), "Already exist",
                e.getMessage());
        return ResponseEntity.status(HttpStatus.CONTINUE).body(result);
    }

    @ExceptionHandler(WrongException.class)
    public ResponseEntity<?> on(WrongException e) {
        final ResponseMessage result = new ResponseMessage(HttpStatus.CONFLICT.value(), "Something went wrong",
                e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    @ExceptionHandler(OkResponse.class)
    public ResponseEntity<?> on(OkResponse e) {
        final ResponseMessage result = new ResponseMessage(HttpStatus.OK.value(), "All successfully done ",
                e.getMessage());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
}