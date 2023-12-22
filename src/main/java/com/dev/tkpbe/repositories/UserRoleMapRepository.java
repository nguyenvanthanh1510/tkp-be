package com.dev.tkpbe.repositories;

import com.dev.tkpbe.commons.enums.RoleType;
import com.dev.tkpbe.models.entities.UserEntity;
import com.dev.tkpbe.models.entities.UserRoleMapEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRoleMapRepository extends JpaRepository<UserRoleMapEntity, Long> {

  @Query("FROM UserRoleMapEntity urm WHERE urm.user = :user")
  List<UserRoleMapEntity> findByUser(UserEntity user);

  @Modifying
  @Transactional
  @Query("DELETE FROM UserRoleMapEntity urm WHERE urm.user = :user")
  void deleteByUser(UserEntity user);
}
