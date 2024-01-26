package com.dev.tkpbe.services.impl;

import com.dev.tkpbe.commons.enums.TimeStatus;
import com.dev.tkpbe.commons.enums.TimeType;
import com.dev.tkpbe.models.entities.UserEntity;
import com.dev.tkpbe.models.responses.DashBoardResponse;
import com.dev.tkpbe.repositories.TimeRepository;
import com.dev.tkpbe.repositories.UserRepository;
import com.dev.tkpbe.services.DashBoardService;
import com.dev.tkpbe.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class DashBoardServiceImpl implements DashBoardService {

    @Lazy private final UserService userService;
    @Lazy private final UserRepository userRepository;
    @Lazy private final TimeRepository timeRepository;

    @Override
    public DashBoardResponse getDashBoard() {
        String userEmail = userService.getAuthenticatedUserEmail();
        UserEntity loggedUser =
                userRepository
                        .findByEmail(userEmail)
                        .orElse(null);
        Long userId = loggedUser.getId();

        Long totalGoLate = timeRepository.countTime(TimeType.CHECK_IN, TimeStatus.LATE,userId);
        Long totalEarly = timeRepository.countTime(TimeType.CHECK_OUT,TimeStatus.EARLY,userId);
        DashBoardResponse dashBoardResponse = DashBoardResponse.builder()
                .totalGoLate(totalGoLate)
                .totalArriveEarly(totalEarly)
                .build();

        return dashBoardResponse;

    }
}
