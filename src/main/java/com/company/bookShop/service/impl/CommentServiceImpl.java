package com.company.bookShop.service.impl;

import com.company.bookShop.config.JwtTokenProvider;
import com.company.bookShop.config.NetworkDataService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final static Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);
    private final NetworkDataService networkDataService;
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
    public CommentsResDto createComment(Integer bookId, CommentsReqDto commentsReqDto, HttpServletRequest httpServletRequest) {
        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Comment Created \t\t {}", commentsReqDto);
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
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

        LOG.error("Comment  Not Found or it isn't your comment");
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
        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("GetComments List \t\t {}", "");
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
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
            LOG.error("Comments  Not Found ");
            throw new NotFoundException("No Comments yet to  This Book");
        }
        return comments;
    }
    /***
     * Update Comment
     * @param id of comment which we need to updateBasketByID
     * @param commentReqDto New details of old comment
     * @param httpServletRequest Define to Device
     * @return Data of updated book
     */
    @Override
    public CommentsResDto updateComment(Integer id, CommentsReqDto commentReqDto, HttpServletRequest httpServletRequest) {
        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Comment Updated \t\t {}", commentReqDto);
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        String username = JwtTokenProvider.getCurrentUser();
        Optional<Comments> comments = commentsRepository.findById(id);
        if (comments.isPresent()&&comments.get().getCreatedBy().equals(username)) {
            Comments entity = commentMapper.updateFromDto(commentReqDto,comments.get());
            commentsRepository.save(entity);
            return commentMapper.toDto(entity);
        }
        LOG.error("Comment  Not Found or it isn't your comment");
        throw new NotFoundException("Comment Not Found or it isn't your comment");
    }

    /***
     * Delete Comment
     * @param id Of deleted comment
     * @param httpServletRequest Define to Device
     */
    @Override
    public void deleteComment(Integer id, HttpServletRequest httpServletRequest) {
        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Comment Deleted \t\t {}", "");
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        Optional<Comments> byId = commentsRepository.findById(id);
        if (byId.isPresent()&&byId.get().getCreatedBy().equals(JwtTokenProvider.getCurrentUser())) {
            commentsRepository.delete(byId.get());
            throw new OkResponse("Successfully Deleted");
        }
        LOG.error("Comment  Not Found or it isn't your comment");
        throw new NotFoundException("Comment Not Found or it isn't your comment");
    }
}