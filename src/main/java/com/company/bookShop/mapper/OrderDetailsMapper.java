package com.company.bookShop.mapper;

import com.company.bookShop.dto.orderDetails.OrderDetailsReqDto;
import com.company.bookShop.dto.orderDetails.OrderDetailsResDto;
import com.company.bookShop.entity.OrderDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper extends EntityMapping<OrderDetails, OrderDetailsReqDto, OrderDetailsResDto> {
}
