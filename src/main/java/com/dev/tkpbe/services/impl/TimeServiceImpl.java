package com.dev.tkpbe.services.impl;

import com.dev.tkpbe.commons.constants.DsdConstant;
import com.dev.tkpbe.components.TimeMapper;
import com.dev.tkpbe.configs.exceptions.DsdCommonException;
import com.dev.tkpbe.models.dtos.Time;
import com.dev.tkpbe.models.dtos.User;
import com.dev.tkpbe.models.entities.TimeEntity;
import com.dev.tkpbe.repositories.TimeRepository;
import com.dev.tkpbe.services.TimeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TimeServiceImpl implements TimeService {

    @Lazy private final TimeRepository timeRepository;
    @Lazy private final TimeMapper timeMapper;

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
        return Optional.of(time)
                .map(timeMapper::toEntity)
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
}
