package com.company.bookShop.mapper;

import com.company.bookShop.dto.basket.BasketReqDto;
import com.company.bookShop.dto.basket.BasketResDto;
import com.company.bookShop.entity.Basket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasketMapping extends EntityMapping<Basket, BasketReqDto, BasketResDto> {}