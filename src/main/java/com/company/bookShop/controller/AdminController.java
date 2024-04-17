package com.company.bookShop.controller;

import com.company.bookShop.dto.PermissionDto;
import com.company.bookShop.dto.role.RoleReqDto;
import com.company.bookShop.service.PermissionService;
import com.company.bookShop.service.RoleService;
import com.company.bookShop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("superAdmin/")
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;
    private final PermissionService permissionService;

    @PostMapping("create/role")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> createRole(@RequestBody RoleReqDto roleReqDto, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(roleService.createRole(roleReqDto,httpServletRequest));
    }
    @PostMapping("create/permission")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> createPermission(@RequestBody PermissionDto dto, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(permissionService.createPermission(dto,httpServletRequest));
    }
    @PutMapping("give/permissions")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> givePermission(@RequestParam Long  roleId,
                                            @RequestBody List<Long> permissionIds) {
        return ResponseEntity.ok(permissionService.givePermissions(roleId,permissionIds));
    }
    @GetMapping("getRoles")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }
    @GetMapping("delete/roles/{id}")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("get/role")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> deleteRole(@RequestParam Long  userId ,@RequestBody List<Long>  roleId) {
        return ResponseEntity.ok(roleService.getRole(userId,roleId));
    }
    @GetMapping("getUsers/active")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
    @PutMapping("block/user/{id}")
    @PreAuthorize("hasAuthority('CAN_ALL')")
    public ResponseEntity<?> blockUser(@PathVariable ("id") Long id,HttpServletRequest httpServletRequest) {
        userService.BlockOrUnblock(id,httpServletRequest);
        return ResponseEntity.ok().build();
    }
}