package com.company.bookShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderStatsResDto {
private String bookName;
private Long totalQuantity;
private Long totalSum;
}
