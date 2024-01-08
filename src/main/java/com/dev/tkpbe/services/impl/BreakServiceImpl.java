package com.dev.tkpbe.services.impl;

import com.dev.tkpbe.commons.constants.DsdConstant;
import com.dev.tkpbe.components.BreakMapper;
import com.dev.tkpbe.configs.exceptions.DsdCommonException;
import com.dev.tkpbe.models.dtos.Break;
import com.dev.tkpbe.models.dtos.Time;
import com.dev.tkpbe.models.entities.BreakEntity;
import com.dev.tkpbe.models.entities.TimeEntity;
import com.dev.tkpbe.repositories.BreakRepository;
import com.dev.tkpbe.services.BreakService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BreakServiceImpl implements BreakService {
    @Lazy private final BreakRepository breakRepository;
    @Lazy private final BreakMapper breakMapper;

    @Override
    public Page<Break> getByPaging(
            int pageNo, int pageSize, String sortBy, String sortDirection) {
        Pageable pageable =
                PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return breakRepository.findAll(pageable).map(breakMapper::toDTO);
    }
    @Override
    public Break getById(Long id) {

        return Optional.ofNullable(id)
                .flatMap(e -> breakRepository.findById(id))
                .map(breakMapper::toDTO)
                .orElse(null);
    }
    @Override
    public Break create(Break breaks){
        if (breaks == null) {
            throw new DsdCommonException(DsdConstant.ERROR.USER.EXIST);
        }
        return Optional.of(breaks)
                .map(breakMapper::toEntity)
                .map(breakRepository::save)
                .map(breakMapper::toDTO)
                .orElse(null);
    }
    @Override
    public Break update(Break breaks){
        BreakEntity oldBreakEntity=breakRepository.findById(breaks.getId()).orElse(null);
        return Optional.of(oldBreakEntity)
                .map(t -> t.toBuilder()
                        .day(breaks.getDay())
                        .breaktype(breaks.getBreaktype())
                        .content(breaks.getContent())
                        .build())
                .map(breakRepository::save)
                .map(breakMapper::toDTO)
                .orElse(null);
    }
    @Override
    public void delete( Long id){
        if (!breakRepository.existsById(id)) {
            throw new DsdCommonException(DsdConstant.ERROR.USER.NOT_EXIST);
        }
        breakRepository.deleteById(id);
    }
}
