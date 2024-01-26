package com.dev.tkpbe.services.impl;

import com.dev.tkpbe.commons.constants.TkpConstant;
import com.dev.tkpbe.commons.enums.RoleType;
import com.dev.tkpbe.components.RoleMapper;
import com.dev.tkpbe.configs.exceptions.TkpCommonException;
import com.dev.tkpbe.models.dtos.Role;
import com.dev.tkpbe.models.entities.RoleEntity;
import com.dev.tkpbe.repositories.RoleRepository;
import com.dev.tkpbe.services.RoleService;
import io.micrometer.common.util.StringUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

  @Autowired private RoleRepository roleRepository;

  @Autowired private RoleMapper roleMapper;

  @Override
  public Page<Role> getByPaging(int pageNo, int pageSize, String sortBy) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    return roleRepository.findAll(pageable).map(dao -> roleMapper.toDTO(dao));
  }

  @Override
  public Role getById(Long id) {
    return Optional.ofNullable(id)
        .flatMap(e -> roleRepository.findById(e).map(b -> roleMapper.toDTO(b)))
        .orElse(null);
  }

  @Override
  public Role create(Role role) {
    if (role == null || roleRepository.existsByType(role.getType())) {
      throw new TkpCommonException(TkpConstant.ERROR.ROLE.EXIST);
    }
    return Optional.ofNullable(role)
        .map(e -> roleMapper.toEntity(e))
        .map(e -> roleRepository.save(e))
        .map(e -> roleMapper.toDTO(e))
        .orElse(null);
  }

  @Override
  public Role update(Role role) {
    RoleEntity oldRoleEntity = roleRepository.findById(role.getId()).orElse(null);
    if (oldRoleEntity == null) {
      throw new TkpCommonException(TkpConstant.ERROR.ROLE.NOT_EXIST);
    }
    return Optional.ofNullable(oldRoleEntity)
        .map(op -> op.toBuilder().name(role.getName()).build())
        .map(roleRepository::save)
        .map(roleMapper::toDTO)
        .orElse(null);
  }

  @Override
  public void delete(Long id) {
    if (StringUtils.isBlank(id.toString())) {
      return;
    }
    roleRepository.deleteById(id);
  }

  @Override
  public Role getUserRole() {
    return roleRepository.findByType(RoleType.USER).map(r -> roleMapper.toDTO(r)).orElse(null);
  }

  @Override
  public Role createUserRole() {
    return Optional.of(
            RoleEntity.builder().type(RoleType.USER).name(RoleType.USER.getValue()).build())
        .map(r -> roleRepository.save(r))
        .map(re -> roleMapper.toDTO(re))
        .orElse(null);
  }

  @Override
  public Role getAdminRole() {
    return roleRepository.findByType(RoleType.ADMIN).map(r -> roleMapper.toDTO(r)).orElse(null);
  }

  @Override
  public Role createAdminRole() {
    return Optional.of(
            RoleEntity.builder().type(RoleType.ADMIN).name(RoleType.ADMIN.getValue()).build())
        .map(r -> roleRepository.save(r))
        .map(re -> roleMapper.toDTO(re))
        .orElse(null);
  }
}
