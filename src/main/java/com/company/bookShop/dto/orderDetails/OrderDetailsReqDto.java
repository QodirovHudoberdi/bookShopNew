package com.company.bookShop.dto.orderDetails;

import lombok.Data;

@Data
public class OrderDetailsReqDto {
    private Integer bookId;
    private Integer quantity;
    private Integer total;

}
