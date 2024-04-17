package com.company.bookShop.repository;

import com.company.bookShop.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket,Integer> {
    List<Basket> findAllByCreatedBy(String username);
}
