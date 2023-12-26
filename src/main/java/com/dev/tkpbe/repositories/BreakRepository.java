package com.dev.tkpbe.repositories;

import com.dev.tkpbe.models.entities.BreakEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakRepository extends JpaRepository<BreakEntity, Long> {

}
