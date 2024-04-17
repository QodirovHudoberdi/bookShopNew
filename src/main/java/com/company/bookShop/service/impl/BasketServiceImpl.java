package com.company.bookShop.service.impl;

import com.company.bookShop.dto.basket.BasketReqDto;
import com.company.bookShop.dto.basket.BasketResDto;
import com.company.bookShop.entity.Basket;
import com.company.bookShop.entity.BooksEntity;
import com.company.bookShop.entity.User;
import com.company.bookShop.exps.WrongException;
import com.company.bookShop.mapper.BasketMapping;
import com.company.bookShop.mapper.BookMapping;
import com.company.bookShop.mapper.UserMapping;
import com.company.bookShop.repository.BasketRepository;
import com.company.bookShop.repository.BookRepository;
import com.company.bookShop.repository.UserRepository;
import com.company.bookShop.service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public final class BasketServiceImpl implements BasketService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookMapping bookMapping;
    private final UserMapping userMapping;
    private final BasketMapping basketMapping;
    private final BasketRepository basketRepository;

    /***
     * Create Basket
     * @param basketReqDto Details of new Basket
     * @param httpServletRequest Define to Device
     * @return Details of new created Basket
     */
    @Override
    public BasketResDto createBaskets(BasketReqDto basketReqDto, HttpServletRequest httpServletRequest) {

        Optional<BooksEntity> byId = bookRepository.findById(basketReqDto.getBookId());
        if (byId.isPresent()) {
            BooksEntity book = byId.get();
            Basket basketEntity = basketMapping.toEntity(basketReqDto);
            basketEntity.setTotal(book.getPrice() * basketEntity.getQuantity());
            Basket save = basketRepository.save(basketEntity);
            BasketResDto dto = basketMapping.toDto(save);
            dto.setBook(bookMapping.toDto(book));
            User user = userRepository.findByUsername(save.getCreatedBy());
            dto.setUser(userMapping.toDto(user));
            return dto;
        }
        throw new WrongException("This Book is Not Found");
    }

    /***
     * Update Basket by Id
     * @param id id of updated basket
     * @param basketReqDto new Details of Basket
     * @param httpServletRequest Define to Device
     * @return Details of updated Basket
     */
    @Override
    public BasketResDto updateBasketByID(Integer id, BasketReqDto basketReqDto, HttpServletRequest httpServletRequest) {
        Optional<Basket> basket1 = basketRepository.findById(id);

        if (basket1.isPresent()) {
            Basket basket = basket1.get();
            basketMapping.updateFromDto(basketReqDto, basket);


            Optional<BooksEntity> byId1 = bookRepository.findById(basket.getBookId());
            if (byId1.isEmpty()) {
                throw new WrongException("Not Found book");
            }
            BooksEntity book = byId1.get();
            basket.setTotal(basketReqDto.getQuantity()*book.getPrice());
            BasketResDto dto = basketMapping.toDto(basket);
            dto.setBook(bookMapping.toDto(book));
            User user = userRepository.findByUsername(basket.getCreatedBy());
            dto.setUser(userMapping.toDto(user));

            basketRepository.save(basket);

            return dto;
        }
        throw new WrongException("This Book is Not Found");
    }
}
