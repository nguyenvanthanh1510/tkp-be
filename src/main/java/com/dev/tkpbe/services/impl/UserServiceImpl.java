package com.dev.tkpbe.services.impl;

import java.util.List;
import java.util.Optional;

import com.dev.tkpbe.commons.constants.TkpConstant;
import com.dev.tkpbe.components.UserMapper;
import com.dev.tkpbe.configs.exceptions.TkpCommonException;
import com.dev.tkpbe.models.dtos.User;
import com.dev.tkpbe.models.entities.UserEntity;
import com.dev.tkpbe.models.entities.UserRoleMapEntity;
import com.dev.tkpbe.repositories.UserRepository;
import com.dev.tkpbe.repositories.UserRoleMapRepository;
import com.dev.tkpbe.services.UserRoleMapService;
import com.dev.tkpbe.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Lazy @Autowired
  UserRepository userRepository;
  @Lazy @Autowired
  UserMapper userMapper;

  @Lazy @Autowired
  UserRoleMapService userRoleMapService;
  @Lazy @Autowired
  UserRoleMapRepository userRoleMapRepository;

  @Override
  public String getAuthenticatedUserEmail() throws UsernameNotFoundException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      if (userDetails != null) {
        String loggedEmail = userDetails.getUsername();
        log.info("LOGGED user: {}", loggedEmail);
        return loggedEmail;
      } else {
        throw new UsernameNotFoundException(TkpConstant.ERROR.AUTH.NOT_FOUND);
      }
    }
    throw new UsernameNotFoundException(TkpConstant.ERROR.AUTH.NOT_FOUND);
  }

  @Override
  public Page<User> getByPaging(
      int pageNo, int pageSize, String sortBy, String sortDirection) {
    Pageable pageable =
        PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
    return userRepository.findAll(pageable).map(userMapper::toDTO);
  }

  @Override
  public User getById(Long id) {

    return Optional.ofNullable(id)
        .flatMap(e -> userRepository.findById(id))
        .map(userMapper::toDTO)
        .orElse(null);
  }

  @Override
  public User create(User user) {
    if (user == null
        || userRepository.existsByEmail(user.getEmail())
        || userRepository.existsByUserName(user.getUserName())) {
      throw new TkpCommonException(TkpConstant.ERROR.USER.EXIST);
    }
    return Optional.of(user)
        .map(userMapper::toEntity)
        .map(userRepository::save)
        .map(
            e -> {
              userRoleMapService.setUserRoleForUser(e.getId());
              return e;
            })
        .map(userMapper::toDTO)
        .orElse(null);
  }

  @Override
  public User update(User user) {
    User toUpdateUser = user.toBuilder().build();


    if (!userRepository.existsById(toUpdateUser.getId())) {
      throw new TkpCommonException(TkpConstant.ERROR.USER.NOT_EXIST);
    }
    return Optional.of(toUpdateUser)
        .flatMap(u -> userRepository.findById(u.getId()))
        .map(
            ue ->
                ue.toBuilder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phone(user.getPhone())
                    .build())
        .map(userRepository::save)
        .map(userMapper::toDTO)
        .orElse(null);
  }

  @Override
  public void delete(@NonNull Long id) {

    if (!userRepository.existsById(id)) {
      throw new TkpCommonException(TkpConstant.ERROR.USER.NOT_EXIST);
    }

    userRepository.deleteById(id);
  }

  @Override
  public User findLoggedInfoByEmail(String email) {
    if (!StringUtils.hasText(email)) {
      return null;
    }

    UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
    if (userEntity == null || userEntity.getUserRoleMap() == null) {
      return null;
    }

    List<UserRoleMapEntity> userRoleMapEntities = userRoleMapRepository.findByUser(userEntity);
    log.info("User role's size: {}", userRoleMapEntities.size());

    userEntity.setUserRoleMap(userRoleMapEntities);
    return userMapper.toDTO(userEntity);
  }

  @Override
  public User createAdmin(User user) {
    return Optional.ofNullable(user)
        .map(userMapper::toEntity)
        .map(userRepository::save)
        .map(
            e -> {
              userRoleMapService.setAdminRoleForUser(e.getId());
              return e;
            })
        .map(userMapper::toDTO)
        .orElse(null);
  }

  @Override
  public User getByEmail(@NonNull String email) {
    return userRepository.findByEmail(email).map(userMapper::toDTO).orElse(null);
  }

}
