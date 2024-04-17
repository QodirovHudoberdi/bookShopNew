package com.company.bookShop.service.impl;

import com.company.bookShop.dto.CommentStatsResDto;
import com.company.bookShop.dto.OrderStatsResDto;
import com.company.bookShop.dto.StatsResDto;
import com.company.bookShop.service.StatsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final EntityManager entityManager;

    /**
     * Get Most Active User On DB
     * @param httpServletRequest Define to Device
     * @return List of Active users
     */
    @Override
    public List<StatsResDto> getActiveUser(HttpServletRequest httpServletRequest) {

        String sql = """
                 WITH orders AS (
                    SELECT
                        od.created_by,
                        SUM(od.amount) AS bookCount,
                        SUM(od.total_sum) AS totalAmount
                    FROM
                        order_details od
                    GROUP BY
                        od.created_by
                ),
                     comments AS (
                         SELECT
                             c.created_by,
                             COUNT(*) AS comment_count
                         FROM
                             comment c
                         GROUP BY
                             c.created_by
                     )
                SELECT
                    u.id AS id,
                    u.first_name AS name,
                    COALESCE(orders.bookCount, 0) AS bookCount,
                    COALESCE(orders.totalAmount, 0) AS totalAmount,
                    COALESCE(comments.comment_count, 0) AS commentCount
                FROM
                    users u
                        LEFT JOIN orders ON u.username = orders.created_by
                        LEFT JOIN comments ON u.username = comments.created_by
                """;
        Query nativeQuery = entityManager.createNativeQuery(sql, StatsResDto.class);
        return nativeQuery.getResultList();
    }

    /**
     * Get Books Which have Most Comments
     * @param httpServletRequest  Define to Device
     * @return List of books
     */
    @Override
    public List<CommentStatsResDto> getMostComments(HttpServletRequest httpServletRequest) {
        String sql = """
                    SELECT
                    b.name                         AS book_name,
                    COALESCE(comments_count, 0)     AS comments_count
                FROM book b
                         JOIN
                     order_details od ON b.id = od.book_id
                         LEFT JOIN
                     (SELECT book_id, COUNT(*) AS comments_count FROM comment GROUP BY book_id) AS c ON b.id = c.book_id
                GROUP BY b.id, b.name, comments_count
                ORDER BY comments_count DESC
                """;
        Query nativeQuery=entityManager.createNativeQuery(sql,CommentStatsResDto.class);
        return nativeQuery.getResultList();
    }

    /**
     * Get best sell  books
     * @param httpServletRequest  Define to Device
     * @return List of best books
     */
    @Override
    public List<OrderStatsResDto> getMostOrders(HttpServletRequest httpServletRequest) {
        String sql = """
        SELECT  b.name AS bookName,
                SUM(o.amount) AS totalQuantity,
                SUM(o.total_sum) AS total_sum
        FROM book b
                 JOIN order_details o ON b.id = o.book_id
        GROUP BY b.name, total_sum
        ORDER BY SUM(o.amount) DESC
        LIMIT 5
        """;
        Query nativeQuery=entityManager.createNativeQuery(sql,OrderStatsResDto.class);
        return nativeQuery.getResultList();


    }
}
