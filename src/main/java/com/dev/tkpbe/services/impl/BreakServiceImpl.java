package com.dev.tkpbe.services.impl;

import com.dev.tkpbe.commons.constants.TkpConstant;
import com.dev.tkpbe.commons.enums.BreakStatus;
import com.dev.tkpbe.commons.enums.BreakType;
import com.dev.tkpbe.components.BreakMapper;
import com.dev.tkpbe.configs.exceptions.TkpCommonException;
import com.dev.tkpbe.models.dtos.Break;
import com.dev.tkpbe.models.entities.BreakEntity;
import com.dev.tkpbe.repositories.BreakRepository;
import com.dev.tkpbe.repositories.UserRepository;
import com.dev.tkpbe.services.BreakService;
import com.dev.tkpbe.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BreakServiceImpl implements BreakService {
    @Lazy private final BreakRepository breakRepository;
    @Lazy private final BreakMapper breakMapper;
    @Lazy private final UserService userService;
    @Lazy private final UserRepository userRepository;

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
        Date now=new Date();
       BreakEntity breakEntity=breakMapper.toEntity(breaks);
        String userEmail=userService.getAuthenticatedUserEmail();
        breakEntity.setDay(now);
        breakEntity.setStatus(BreakStatus.NEW);
        userRepository.findByEmail(userEmail).ifPresent(breakEntity::setUser);
        return Optional.of(breakEntity)
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
                        .type(breaks.getType())
                        .content(breaks.getContent())
                        .build())
                .map(breakRepository::save)
                .map(breakMapper::toDTO)
                .orElse(null);
    }
    @Override
    public void delete( Long id){
        if (!breakRepository.existsById(id)) {
            throw new TkpCommonException(TkpConstant.ERROR.USER.NOT_EXIST);
        }
        breakRepository.deleteById(id);
    }

    @Override
    public Break updateStatus(Long id){
        BreakEntity oldBreakEntity=breakRepository.findById(id).orElse(null);
        return Optional.of(oldBreakEntity)
                .map(t -> t.toBuilder()
                        .status(BreakStatus.APPROVE)
                        .build())
                .map(breakRepository::save)
                .map(breakMapper::toDTO)
                .orElse(null);
    }
}
