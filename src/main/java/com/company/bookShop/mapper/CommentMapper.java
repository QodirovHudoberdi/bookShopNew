package com.company.bookShop.mapper;

import com.company.bookShop.dto.comments.CommentsReqDto;
import com.company.bookShop.dto.comments.CommentsResDto;
import com.company.bookShop.entity.Comments;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapping<Comments, CommentsReqDto, CommentsResDto> {
}
