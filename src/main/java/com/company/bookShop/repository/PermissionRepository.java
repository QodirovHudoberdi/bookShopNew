package com.company.bookShop.repository;

import com.company.bookShop.entity.Permission;
import com.company.bookShop.service.PermissionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission , Long> {
    Permission findByName(String name);
}
