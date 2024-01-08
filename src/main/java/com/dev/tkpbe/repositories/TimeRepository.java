package com.dev.tkpbe.repositories;

import com.dev.tkpbe.commons.enums.TimeType;
import com.dev.tkpbe.models.entities.TimeEntity;
import com.dev.tkpbe.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TimeRepository extends JpaRepository<TimeEntity,Long> {
    boolean existsByUserAndTypeAndTimeBetween(UserEntity user, TimeType type, Date startTime, Date endTime);
}
