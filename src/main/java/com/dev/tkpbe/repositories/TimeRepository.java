package com.dev.tkpbe.repositories;

import com.dev.tkpbe.commons.enums.TimeStatus;
import com.dev.tkpbe.commons.enums.TimeType;
import com.dev.tkpbe.models.entities.TimeEntity;
import com.dev.tkpbe.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TimeRepository extends JpaRepository<TimeEntity,Long> {
    boolean existsByUserAndTypeAndTimeBetween(UserEntity user, TimeType type, Date startTime, Date endTime);

    @Query(
            "SELECT count(t) FROM TimeEntity t "
                    + "WHERE (t.user.id = :userId) AND t.type = :type AND t.status= :status")
    Long countTime(@Param("type") TimeType type, @Param("status") TimeStatus status, @Param("userId") Long userId);
}
