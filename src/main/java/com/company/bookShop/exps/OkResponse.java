package com.company.bookShop.exps;

public class OkResponse extends RuntimeException {
    public OkResponse(String message) {
        super(message);
    }
}