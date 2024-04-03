package com.company.bookShop.service;

import com.company.bookShop.dto.login.LoginRequestDto;
import com.company.bookShop.dto.login.LoginResponseDto;
import com.company.bookShop.dto.registration.RegistrationReqDto;
import com.company.bookShop.dto.registration.RegistrationResDto;
import com.company.bookShop.entity.User;
import com.company.bookShop.exps.UserException;
import com.company.bookShop.mapper.RegistrationMapper;
import com.company.bookShop.repository.RoleRepository;
import com.company.bookShop.repository.UserRepository;
import com.company.bookShop.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RegistrationMapper registrationMapper;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto,
                                  HttpServletRequest httpServletRequest
    ) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername());
        if (user == null) {
            throw new UserException("User Not Found");
        }
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setId(user.getId());
        loginResponseDto.setUsername(user.getUsername());
        loginResponseDto.setFirstName(user.getFirstName());
        loginResponseDto.setLastName(user.getLastName());
        loginResponseDto.setToken(JwtUtil.encode(user.getUsername()));
        if (loginRequestDto.isRememberMe()) {
            user.setRefreshToken(JwtUtil.getRefreshToken(loginResponseDto));
        }

        user.setIsActive(true);

        userRepository.save(user);
        return loginResponseDto;
    }

    @Override
    public RegistrationResDto registration(RegistrationReqDto registrationReqDto, HttpServletRequest httpServletRequest) {
        User user = userRepository.findByUsername(registrationReqDto.getUsername());
        if (user != null) {
            throw new UserException("This username is busy");
        }
        User entity = registrationMapper.toEntity(registrationReqDto);
        userRepository.save(entity);
        return registrationMapper.toDto(entity);
    }

}
