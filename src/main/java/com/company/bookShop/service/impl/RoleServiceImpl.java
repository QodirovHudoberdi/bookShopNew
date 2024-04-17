package com.company.bookShop.service.impl;

import com.company.bookShop.config.NetworkDataService;
import com.company.bookShop.dto.role.RoleReqDto;
import com.company.bookShop.dto.role.RoleResDto;
import com.company.bookShop.dto.user.UserResponseDto;
import com.company.bookShop.entity.Role;
import com.company.bookShop.entity.User;
import com.company.bookShop.exps.AlreadyExistException;
import com.company.bookShop.exps.NotFoundException;
import com.company.bookShop.exps.OkResponse;
import com.company.bookShop.mapper.RoleMapper;
import com.company.bookShop.mapper.UserMapping;
import com.company.bookShop.repository.RoleRepository;
import com.company.bookShop.repository.UserRepository;
import com.company.bookShop.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final static Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);
    private final NetworkDataService networkDataService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserMapping userMapping;

    /**
     * Create Role on DB
     * @param roleReqDto Details of new role
     * @param httpServletRequest detail device
     * @return New Role From DB
     */
    @Override
    public RoleResDto createRole(RoleReqDto roleReqDto, HttpServletRequest httpServletRequest) {

        String ClientInfo = networkDataService.getClientIPv4Address(httpServletRequest);
        String ClientIP = networkDataService.getRemoteUserInfo(httpServletRequest);
        LOG.info("Comment Created \t\t {}", roleReqDto);
        LOG.info("Client host : \t\t {}", ClientInfo);
        LOG.info("Client IP :  \t\t {}", ClientIP);
        Role role = roleRepository.findByName(roleReqDto.getName());
        if (role!=null) {
            LOG.error("This ROLE Already Created");
            throw new AlreadyExistException("This role already created");
        }
        Role role1= new Role();
        role1.setName(roleReqDto.getName());
        Role save = roleRepository.save(role1);
        return roleMapper.toDto(save);
    }

    /**
     * Get list of roles from dB
     * @return List of Roles
     */
    @Override
    public List<RoleResDto> getRoles() {
        List<Role> all = roleRepository.findAll();
        return  roleMapper.toDto(all);
    }
/**
 *
 * Delete Role from DB
 */
    @Override
    public void deleteRole(Long id) {
        Optional<Role> byId = roleRepository.findById(id);
        if (byId.isPresent()) {
            roleRepository.delete(byId.get());
            throw new OkResponse("Successfully Deleted");
        }
        throw new NotFoundException("Role Not Found ");
    }

    /**
     * Get Role
     *
     * @param userId id of User which changed RoleId
     * @param roleId id of Role
     * @return User details with Changed Roles
     */
    @Override
    public UserResponseDto getRole(Long userId, List<Long> roleId) {
        Set<Role> roles=new HashSet<>();
        Optional<User> user = userRepository.findById(userId);
        for (Long id : roleId) {
            Optional<Role> role = roleRepository.findById(id);
            role.ifPresent(roles::add);
        }
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setRoles(roles);
            userRepository.save(user1);

        return userMapping.toDto(user1);
        }
        throw new NotFoundException("user Not Found");
    }
}
