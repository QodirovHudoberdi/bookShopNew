package com.company.bookShop.repository;

import com.company.bookShop.entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<BooksEntity,Integer> {
     BooksEntity findByName(String name);

}