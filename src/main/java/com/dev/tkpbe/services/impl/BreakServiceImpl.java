package com.dev.tkpbe.services.impl;

import com.dev.tkpbe.commons.constants.DsdConstant;
import com.dev.tkpbe.components.BreakMapper;
import com.dev.tkpbe.configs.exceptions.DsdCommonException;
import com.dev.tkpbe.models.dtos.Break;
import com.dev.tkpbe.models.dtos.Time;
import com.dev.tkpbe.repositories.BreakRepository;
import com.dev.tkpbe.services.BreakService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
        Break toUpdateBreak = breaks.toBuilder().build();

        if(toUpdateBreak.getId() == null){
            throw new DsdCommonException(DsdConstant.ERROR.USER.NOT_EXIST);
        }
        return Optional.of(breaks)
                .map(breakMapper::toEntity)
                .map(breakRepository::save)
                .map(breakMapper::toDTO)
                .orElse(null);
    }
    @Override
    public void delete(@NonNull Long id){
        if (!breakRepository.existsById(id)) {
            throw new DsdCommonException(DsdConstant.ERROR.USER.NOT_EXIST);
        }
        breakRepository.deleteById(id);
    }
}
