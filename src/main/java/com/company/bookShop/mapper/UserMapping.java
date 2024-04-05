package com.company.bookShop.mapper;

import com.company.bookShop.dto.user.UserRequestDto;
import com.company.bookShop.dto.user.UserResponseDto;
import com.company.bookShop.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapping extends EntityMapping<User, UserRequestDto, UserResponseDto> {
}
