package com.company.bookShop.service;

import com.company.bookShop.dto.comments.CommentsDto;
import com.company.bookShop.dto.comments.CommentsReqDto;
import com.company.bookShop.dto.comments.CommentsResDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CommentService {
    CommentsResDto create(Integer bookId, CommentsReqDto commentsReqDto, HttpServletRequest httpServletRequest);

    List<CommentsDto> getComments(Integer bookId, HttpServletRequest httpServletRequest);

    CommentsResDto update(Integer id, CommentsReqDto commentReSDto, HttpServletRequest httpServletRequest);

    void delete(Integer id, HttpServletRequest httpServletRequest);
}
