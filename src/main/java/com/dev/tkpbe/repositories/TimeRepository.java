package com.dev.tkpbe.repositories;

import com.dev.tkpbe.models.entities.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends JpaRepository<TimeEntity,Long> {

}
