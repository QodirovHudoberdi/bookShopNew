package com.company.bookShop.repository;

import com.company.bookShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    User findByRefreshToken(String refreshToken);
    List<User> findAllByIsActive(boolean active );
}
