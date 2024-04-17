package com.company.bookShop.repository;

import com.company.bookShop.entity.OrderDetails;
import com.company.bookShop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Integer> {

    @Query(value = "select * from order_details where order_id=:id", nativeQuery = true)
    List<OrderDetails> findByOrderId(Integer id);

    @Query(value = "select * from order_details where created_by=:created_by", nativeQuery = true)
    List<OrderDetails> findByCreatedBy(String created_by);
}
