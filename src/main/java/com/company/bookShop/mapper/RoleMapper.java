package com.company.bookShop.mapper;

import com.company.bookShop.dto.role.RoleResDto;
import com.company.bookShop.dto.role.RoleReqDto;
import com.company.bookShop.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapping<Role, RoleReqDto, RoleResDto> {
}
