package com.company.bookShop.service;

import com.company.bookShop.dto.CommentStatsResDto;
import com.company.bookShop.dto.OrderStatsResDto;
import com.company.bookShop.dto.StatsResDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatsService {
    List<StatsResDto> getActiveUser(HttpServletRequest httpServletRequest);

    List<CommentStatsResDto> getMostComments(HttpServletRequest httpServletRequest);

    List<OrderStatsResDto> getMostOrders(HttpServletRequest httpServletRequest);
}
