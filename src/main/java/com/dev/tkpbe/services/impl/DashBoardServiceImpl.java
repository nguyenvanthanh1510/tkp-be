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

import java.time.DayOfWeek;
import java.time.LocalDate;


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

        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        Long totalDay = 0L;
        for (LocalDate date = firstDayOfMonth; !date.isAfter(currentDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                totalDay++;
            }
        }

        Long totalGoLate = timeRepository.countTime(TimeType.CHECK_IN, TimeStatus.LATE,userId);
        Long totalEarly = timeRepository.countTime(TimeType.CHECK_OUT,TimeStatus.EARLY,userId);
        Long countRecordsByCurrentMonthAndYear= timeRepository.countRecordsByCurrentMonthAndYear();
        Long totalDayOff= totalDay-countRecordsByCurrentMonthAndYear;
        DashBoardResponse dashBoardResponse = DashBoardResponse.builder()
                .totalGoLate(totalGoLate)
                .totalArriveEarly(totalEarly)
                .totalDayOff(totalDayOff)

                .build();

        return dashBoardResponse;

    }
}
