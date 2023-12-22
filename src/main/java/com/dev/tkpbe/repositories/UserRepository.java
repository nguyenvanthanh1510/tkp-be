package com.dev.tkpbe.repositories;

import java.util.Optional;
import com.dev.tkpbe.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByUserName(String username);

}
