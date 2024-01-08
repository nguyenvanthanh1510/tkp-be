package com.dev.tkpbe.services.impl;

import com.dev.tkpbe.commons.constants.DsdConstant;
import com.dev.tkpbe.commons.enums.Status;
import com.dev.tkpbe.commons.enums.TimeType;
import com.dev.tkpbe.components.TimeMapper;
import com.dev.tkpbe.configs.exceptions.DsdCommonException;
import com.dev.tkpbe.models.dtos.Time;
import com.dev.tkpbe.models.dtos.User;
import com.dev.tkpbe.models.entities.TimeEntity;
import com.dev.tkpbe.models.entities.UserEntity;
import com.dev.tkpbe.repositories.TimeRepository;
import com.dev.tkpbe.repositories.UserRepository;
import com.dev.tkpbe.services.TimeService;
import com.dev.tkpbe.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TimeServiceImpl implements TimeService {

    @Lazy private final TimeRepository timeRepository;
    @Lazy private final TimeMapper timeMapper;
    @Lazy private final UserService userService;
    @Lazy private final UserRepository userRepository;

    @Override
    public Page<Time> getByPaging(
            int pageNo, int pageSize, String sortBy, String sortDirection) {
        Pageable pageable =
                PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return timeRepository.findAll(pageable).map(timeMapper::toDTO);
    }
    @Override
    public Time getById(Long id) {

        return Optional.ofNullable(id)
                .flatMap(e -> timeRepository.findById(id))
                .map(timeMapper::toDTO)
                .orElse(null);
    }

    @Override
    public Time create(Time time){
        if (time == null) {
            throw new DsdCommonException(DsdConstant.ERROR.USER.EXIST);
        }
        Date currentTime = new Date();
        String selectTime = time.getTime().toString();
        if(selectTime == null) {
            time.setTime(currentTime);
        }
        TimeEntity timeEntity=timeMapper.toEntity(time);
        String userEmail=userService.getAuthenticatedUserEmail();
        userRepository.findByEmail(userEmail).ifPresent(timeEntity::setUser);
        return Optional.of(timeEntity)
                .map(timeRepository::save)
                .map(timeMapper::toDTO)
                .orElse(null);
    }
    @Override
    public Time update(Time time){
        TimeEntity oldTimeEntiy=timeRepository.findById(time.getId()).orElse(null);
        return Optional.of(oldTimeEntiy)
                .map(t -> t.toBuilder()
                        .type(time.getType())
                        .time(time.getTime())
                        .build())
                .map(timeRepository::save)
                .map(timeMapper::toDTO)
                .orElse(null);
    }
    @Override
    public void delete( Long id){
        if (!timeRepository.existsById(id)) {
            throw new DsdCommonException(DsdConstant.ERROR.USER.NOT_EXIST);
        }
        timeRepository.deleteById(id);
    }

    @Override
    public Time checkIn(Time time){
        TimeEntity timeEntity=timeMapper.toEntity(time);
        Date now =new Date();
        String userEmail=userService.getAuthenticatedUserEmail();
        UserEntity user=userRepository.findByEmail(userEmail).orElse(null);
        Date startOfDay = getStartOfDay(now);
        Date endOfDay = getEndOfDay(now);
        boolean hasCheckedInToday = timeRepository.existsByUserAndTypeAndTimeBetween(user, TimeType.CHECK_IN, startOfDay, endOfDay);

        if (hasCheckedInToday) {
            throw new DsdCommonException(DsdConstant.ERROR.TIME.CHECK_IN);
        }
        userRepository.findByEmail(userEmail).ifPresent(timeEntity::setUser);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour <= 9) {
            timeEntity.setStatus(Status.TIMELY);
        } else {
            timeEntity.setStatus(Status.LATE);
        }
        return Optional.of(timeEntity)
                .map(t -> t.setType(TimeType.CHECK_IN))
                .map(t -> t.setTime(now))
                .map(timeRepository::save)
                .map(timeMapper::toDTO)
                .orElse(null);
    }

    @Override
    public Time checkOut(Time time){
        TimeEntity timeEntity=timeMapper.toEntity(time);
        String userEmail=userService.getAuthenticatedUserEmail();
        UserEntity user=userRepository.findByEmail(userEmail).orElse(null);
        Date now =new Date();
        Date startOfDay = getStartOfDay(now);
        Date endOfDay = getEndOfDay(now);

        boolean hasCheckedInToday = timeRepository.existsByUserAndTypeAndTimeBetween(user, TimeType.CHECK_IN, startOfDay, endOfDay);

        if (!hasCheckedInToday) {
            throw new DsdCommonException(DsdConstant.ERROR.TIME.NOT_CHECK_IN);
        }

        boolean hasCheckedOutToday = timeRepository.existsByUserAndTypeAndTimeBetween(user, TimeType.CHECK_OUT, startOfDay, endOfDay);

        if (hasCheckedOutToday) {
            throw new DsdCommonException(DsdConstant.ERROR.TIME.CHECK_OUT);
        }
        userRepository.findByEmail(userEmail).ifPresent(timeEntity::setUser);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 18) {
            timeEntity.setStatus(Status.TIMELY);
        } else {
            timeEntity.setStatus(Status.EARLY);
        }
        return Optional.of(timeEntity)
                .map(t -> t.setType(TimeType.CHECK_OUT))
                .map(t -> t.setTime(now))
                .map(timeRepository::save)
                .map(timeMapper::toDTO)
                .orElse(null);

    }

    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

}
