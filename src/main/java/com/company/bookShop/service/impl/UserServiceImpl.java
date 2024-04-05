package com.company.bookShop.service.impl;

import com.company.bookShop.config.JwtTokenProvider;
import com.company.bookShop.config.NetworkDataService;
import com.company.bookShop.dto.login.LoginRequestDto;
import com.company.bookShop.dto.login.LoginResponseDto;
import com.company.bookShop.dto.registration.RegistrationReqDto;
import com.company.bookShop.dto.registration.RegistrationResDto;
import com.company.bookShop.entity.User;
import com.company.bookShop.exps.AlreadyExistException;
import com.company.bookShop.mapper.RegistrationMapper;
import com.company.bookShop.mapper.RoleMapper;
import com.company.bookShop.repository.UserRepository;
import com.company.bookShop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final NetworkDataService networkDataService;
    private final UserRepository userRepository;
    private final RegistrationMapper registrationMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleMapper roleMapper;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto,
                                  HttpServletRequest httpServletRequest
    ) {
        return jwtTokenProvider.createToken(loginRequestDto.getUsername(), true);
    }
    @Override
    public RegistrationResDto registration(RegistrationReqDto registrationReqDto, HttpServletRequest httpServletRequest) {
        String ClientIP = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientInfo = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        User user = userRepository.findByUsername(registrationReqDto.getUsername());
        if (user != null) {
            LOG.warn("this username used ");
            throw new AlreadyExistException("This username is busy");
        }
        User entity = registrationMapper.toEntity(registrationReqDto);
        userRepository.save(entity);
        LOG.info("Successfully registration",registrationReqDto);

        return registrationMapper.toDto(entity);

    }

}
