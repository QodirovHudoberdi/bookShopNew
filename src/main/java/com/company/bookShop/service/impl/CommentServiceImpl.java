package com.company.bookShop.service.impl;

import com.company.bookShop.config.JwtTokenProvider;
import com.company.bookShop.dto.comments.CommentsDto;
import com.company.bookShop.dto.comments.CommentsReqDto;
import com.company.bookShop.dto.comments.CommentsResDto;
import com.company.bookShop.entity.BooksEntity;
import com.company.bookShop.entity.Comments;
import com.company.bookShop.entity.User;
import com.company.bookShop.exps.NotFoundException;
import com.company.bookShop.exps.OkResponse;
import com.company.bookShop.exps.WrongException;
import com.company.bookShop.mapper.BookMapping;
import com.company.bookShop.mapper.CommentMapper;
import com.company.bookShop.mapper.UserMapping;
import com.company.bookShop.repository.BookRepository;
import com.company.bookShop.repository.CommentsRepository;
import com.company.bookShop.repository.UserRepository;
import com.company.bookShop.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final BookMapping bookMapping;
    private final CommentsRepository commentsRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserMapping userMapping;

    /**
     * Create new comment
     * @param bookId id of book which we need
     * @param commentsReqDto details of new comment
     * @param httpServletRequest  to define device
     * @return write comment  to database
     */
    @Override
    public CommentsResDto create(Integer bookId, CommentsReqDto commentsReqDto, HttpServletRequest httpServletRequest) {
        Optional<BooksEntity> books = bookRepository.findById(bookId);
        String username = JwtTokenProvider.getCurrentUser();
        User user = userRepository.findByUsername(username);
        if (books.isPresent()) {
            BooksEntity book = books.get();

            Comments comment = new Comments();
            comment.setBookId(bookId);
            comment.setContent(commentsReqDto.getContent());
            Comments save = commentsRepository.save(comment);
            CommentsResDto dto = commentMapper.toDto(save);
            dto.setBook(bookMapping.toDto(book));
            dto.setUser(userMapping.toDto(user));
            return dto;
        }
        throw new WrongException("Something went Wrong");
    }

    /**
     * Get Book's Comments
     * @param bookId id of book which we need
     * @param httpServletRequest to define request device
     * @return list of Comments
     */

    @Override
    public List<CommentsDto> getComments(Integer bookId, HttpServletRequest httpServletRequest) {
        List<Comments> allComments = commentsRepository.findAllByBookId(bookId);
        List<CommentsDto> comments = new ArrayList<>();
        for (Comments comment : allComments) {
            CommentsDto commentsResDto = new CommentsDto();
            commentsResDto.setContent(comment.getContent());
            commentsResDto.setId(comment.getId());
            User user = userRepository.findByUsername(comment.getCreatedBy());
            commentsResDto.setUser(userMapping.toDto(user));
            comments.add(commentsResDto);
        }
        if (comments.isEmpty()) {
            throw new NotFoundException("No Comments yet to  This Book");
        }
        return comments;
    }

    @Override
    public CommentsResDto update(Integer id, CommentsReqDto commentReSDto, HttpServletRequest httpServletRequest) {
        String username = JwtTokenProvider.getCurrentUser();
        Optional<Comments> comments = commentsRepository.findById(id);
        if (comments.isPresent()&&comments.get().getCreatedBy().equals(username)) {
            Comments entity = commentMapper.updateFromDto(commentReSDto,comments.get());
            commentsRepository.save(entity);
            return commentMapper.toDto(entity);
        }
        throw new NotFoundException("Comment Not Found or it isn't your book");
    }
    @Override
    public void delete(Integer id,HttpServletRequest httpServletRequest) {
        Optional<Comments> byId = commentsRepository.findById(id);
        if (byId.isPresent()&&byId.get().getCreatedBy().equals(JwtTokenProvider.getCurrentUser())) {
            commentsRepository.delete(byId.get());
            throw new OkResponse("Successfully Deleted");
        }
        throw new NotFoundException("Comment Not Found or it isn't your book");
    }
}