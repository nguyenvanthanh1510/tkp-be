package com.dev.tkpbe.components;


import java.util.Optional;

import com.dev.tkpbe.models.dtos.Role;
import com.dev.tkpbe.models.entities.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
  public Role toDTO(RoleEntity entity) {
    return Optional.ofNullable(entity)
        .map(e -> Role.builder().id(e.getId()).name(e.getName()).type(e.getType()).build())
        .orElse(null);
  }

  public RoleEntity toEntity(Role dto) {
    return Optional.ofNullable(dto)
        .map(e -> RoleEntity.builder().id(e.getId()).name(e.getName()).type(e.getType()).build())
        .orElse(null);
  }
}
