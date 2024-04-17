package com.company.bookShop.dto.order;

import com.company.bookShop.dto.orderDetails.OrderDetailsResDto;
import com.company.bookShop.dto.user.UserResponseDto;
import lombok.Data;

import java.util.List;
@Data
public class OrderResDto {
    private Integer id;
    private List<OrderDetailsResDto> orders;
    private Integer total;
}
