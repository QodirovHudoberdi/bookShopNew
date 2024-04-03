package com.company.bookShop.mapper;

import com.company.bookShop.dto.registration.RegistrationReqDto;
import com.company.bookShop.dto.registration.RegistrationResDto;
import com.company.bookShop.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper extends EntityMapping<User, RegistrationReqDto, RegistrationResDto> {
}
