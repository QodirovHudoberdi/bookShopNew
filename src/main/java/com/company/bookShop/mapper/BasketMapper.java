package com.company.bookShop.mapper;

import com.company.bookShop.dto.basket.BasketDTO;
import com.company.bookShop.dto.basket.BasketReqDto;
import com.company.bookShop.entity.Basket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasketMapper extends EntityMapping<Basket, BasketReqDto, BasketDTO>{
}
