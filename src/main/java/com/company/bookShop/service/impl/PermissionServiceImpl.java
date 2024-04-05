package com.company.bookShop.service.impl;

import com.company.bookShop.dto.PermissionDto;
import com.company.bookShop.dto.role.RoleResDto;
import com.company.bookShop.entity.Permission;
import com.company.bookShop.entity.Role;
import com.company.bookShop.exps.AlreadyExistException;
import com.company.bookShop.exps.NotFoundException;
import com.company.bookShop.mapper.RoleMapper;
import com.company.bookShop.repository.PermissionRepository;
import com.company.bookShop.repository.RoleRepository;
import com.company.bookShop.service.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public PermissionDto create(PermissionDto dto, HttpServletRequest httpServletRequest) {
        Permission byName = permissionRepository.findByName(dto.getName());
        if (byName == null) {
            Permission permission = new Permission();
            permission.setName(dto.getName());
            permissionRepository.save(permission);
            dto.setId(permission.getId());
            return dto;
        }
        throw new AlreadyExistException("This Permission already Create");
    }
    @Override
    public RoleResDto givePermissions(Long roleId, List<Long> permissionIds) {
        Optional<Role> roleById = roleRepository.findById(roleId);
        if (roleById.isEmpty()) {
            throw new NotFoundException("User Not found");
        }
        Role role = roleById.get();
        Set<Permission> permissions = new HashSet<>();
        for (Long permissionId : permissionIds) {
            Optional<Permission> byId = permissionRepository.findById(permissionId);
            if (byId.isPresent()) {
                Permission permission = byId.get();
                permissions.add(permission);
            }
        }
        role.setPermissions(permissions);
        roleRepository.save(role);
        return roleMapper.toDto(role);
    }
}