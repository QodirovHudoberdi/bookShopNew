package com.company.bookShop.service;

import com.company.bookShop.dto.basket.BasketReqDto;
import com.company.bookShop.dto.basket.BasketResDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface BasketService {
    BasketResDto createBaskets(BasketReqDto basketReqDto, HttpServletRequest httpServletRequest);

    BasketResDto updateBasketByID(Integer id, BasketReqDto basketReqDto, HttpServletRequest httpServletRequest);
}
