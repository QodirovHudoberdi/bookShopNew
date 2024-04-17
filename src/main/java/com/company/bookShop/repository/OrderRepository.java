package com.company.bookShop.repository;

import com.company.bookShop.entity.OrderDetails;
import com.company.bookShop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {

    @Query(value = "select * from orders where orders.created_by=:cratedBy", nativeQuery = true)
    List<Orders> getAllOrderByUsername(String cratedBy);

}
