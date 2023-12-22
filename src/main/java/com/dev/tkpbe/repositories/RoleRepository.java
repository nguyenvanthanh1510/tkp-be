package com.dev.tkpbe.repositories;


import com.dev.tkpbe.commons.enums.RoleType;
import com.dev.tkpbe.models.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByType(RoleType type);

  boolean existsByType(RoleType type);
}
