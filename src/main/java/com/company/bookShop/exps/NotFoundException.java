package com.company.bookShop.exps;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}