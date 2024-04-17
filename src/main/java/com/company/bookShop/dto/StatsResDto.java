package com.company.bookShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class StatsResDto {
private Long id;
private String name;
private Long bookCount;
private Long totalAmount;
private Long commentCount;
}
