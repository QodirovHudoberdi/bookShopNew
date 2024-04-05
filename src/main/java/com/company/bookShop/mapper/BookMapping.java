package com.company.bookShop.mapper;


import com.company.bookShop.dto.book.BookResponseDto;
import com.company.bookShop.entity.BooksEntity;
import com.company.bookShop.dto.book.BookRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapping extends EntityMapping<BooksEntity,BookRequestDto,BookResponseDto>{
}
