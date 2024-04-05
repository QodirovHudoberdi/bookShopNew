package com.company.bookShop.repository;

import com.company.bookShop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);

    @Override
    List<Role> findAll();
}
