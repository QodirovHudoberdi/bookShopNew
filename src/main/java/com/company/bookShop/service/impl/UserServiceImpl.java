package com.company.bookShop.service.impl;

import com.company.bookShop.config.JwtTokenProvider;
import com.company.bookShop.config.NetworkDataService;
import com.company.bookShop.dto.login.LoginRequestDto;
import com.company.bookShop.dto.login.LoginResponseDto;
import com.company.bookShop.dto.registration.RegistrationReqDto;
import com.company.bookShop.dto.registration.RegistrationResDto;
import com.company.bookShop.dto.user.UserRequestDto;
import com.company.bookShop.dto.user.UserResponseDto;
import com.company.bookShop.entity.User;
import com.company.bookShop.exps.AlreadyExistException;
import com.company.bookShop.exps.OkResponse;
import com.company.bookShop.exps.WrongException;
import com.company.bookShop.mapper.RegistrationMapper;
import com.company.bookShop.mapper.UserMapping;
import com.company.bookShop.repository.UserRepository;
import com.company.bookShop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final NetworkDataService networkDataService;
    private final UserRepository userRepository;
    private final RegistrationMapper registrationMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapping userMapping;

    /**
     * Login
     * @param loginRequestDto Details of user which have Db
     * @param httpServletRequest Define to Device and User
     * @return
     */
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto,
                                  HttpServletRequest httpServletRequest
    ) {
        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Login  \t\t {}", loginRequestDto);
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        return jwtTokenProvider.createToken(loginRequestDto.getUsername(), true);
    }

    /**
     * Registration
     * @param registrationReqDto Details Of New User
     * @param httpServletRequest Define to Device
     * @return
     */
    @Override
    public RegistrationResDto registration(RegistrationReqDto registrationReqDto, HttpServletRequest httpServletRequest) {
        String ClientIP = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientInfo = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Registration \t\t  {}" , registrationReqDto);
        LOG.info("Client host : \t\t {} ", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        User user = userRepository.findByUsername(registrationReqDto.getUsername());
        if (user != null) {
            LOG.error("this username used ");
            throw new AlreadyExistException("This username is busy");
        }
        User entity = registrationMapper.toEntity(registrationReqDto);
        userRepository.save(entity);
        LOG.info("Successfully registration \t\t {} ",registrationReqDto);

        return registrationMapper.toDto(entity);

    }

    /**
     * Get Active Users
     * @return List Of Active Users
     */
    @Override
    public List<UserResponseDto> getUsers() {
        List<User> allByIsActive = userRepository.findAllByIsActive(true);
        if (allByIsActive.isEmpty()) {
            throw new WrongException("Active Users Not yet on Database");
        }
        return  userMapping.toDto(allByIsActive);
    }

    /**
     * Block or Unblock User
     * @param id To find User which We Need
     * @param httpServletRequest Define to  Device
     */
    @Override
    public void BlockOrUnblock(Long id, HttpServletRequest httpServletRequest) {
        String ClientIP = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientInfo = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Registration \t\t  {}" , "");
        LOG.info("Client host : \t\t {} ", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setIsActive(!user1.getIsActive());
            userRepository.save(user1);
            if (user1.getIsActive()) {
                LOG
                        .info("User SuccessFully Unblocked");
                throw new OkResponse("User Successfully UnBlocked");
            }
            LOG
                    .info("User SuccessFully Blocked");
            throw new OkResponse("User Successfully Blocked");
        }

    }

    /**
     * Update Self Update
     * @param id User id which Updated
     * @param dto new details old User
     * @param httpServletRequest define to Device
     * @return updated details of User
     */
    @Override
    public UserResponseDto updateUserById(Long id, UserRequestDto dto, HttpServletRequest httpServletRequest) {
        String ClientIP = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientInfo = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Update  \t\t  {}" , dto);
        LOG.info("Client host : \t\t {} ", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        Optional<User> userById = userRepository.findById(id);

        if (userById.isPresent()){
            User user = userById.get();
            if (user.getUsername().equals(JwtTokenProvider.getCurrentUser())){
                userMapping.updateFromDto(dto,user);
                User saved = userRepository.save(user);
                return userMapping.toDto(saved);
            }
        }
        LOG.error("Maybe the id is wrong or You Cannot Update this User");
        throw new WrongException("Maybe the id is wrong or You Cannot Update this User");
    }
}