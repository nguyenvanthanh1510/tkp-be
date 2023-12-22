package com.dev.tkpbe.components;

import java.util.Optional;

import com.dev.tkpbe.models.dtos.UserRoleMap;
import com.dev.tkpbe.models.entities.UserRoleMapEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapMapper {

  @Autowired @Lazy private UserMapper userMapper;
  @Autowired @Lazy private RoleMapper roleMapper;

  public UserRoleMap toDTO(UserRoleMapEntity entity) {
    return Optional.ofNullable(entity)
        .map(
            e ->
                UserRoleMap.builder()
                    .id(e.getId())
                    .user(userMapper.toDTO(e.getUser()))
                    .role(roleMapper.toDTO(e.getRole()))
                    .build())
        .orElse(null);
  }

  public UserRoleMapEntity toEntity(UserRoleMap dto) {
    return Optional.ofNullable(dto)
        .map(
            e ->
                UserRoleMapEntity.builder()
                    .id(e.getId())
                    .user(userMapper.toEntity(e.getUser()))
                    .role(roleMapper.toEntity(e.getRole()))
                    .build())
        .orElse(null);
  }
}
