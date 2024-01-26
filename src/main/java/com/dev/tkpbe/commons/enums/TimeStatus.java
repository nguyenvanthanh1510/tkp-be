package com.dev.tkpbe.commons.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Schema(enumAsRef = true)
@RequiredArgsConstructor
@Getter
public enum TimeStatus {
    LATE("GO_LATE"),
    TIMELY("TIMELY"),
    EARLY("ARRIVE_EARLY");

    private final String value;

    public static TimeStatus parse(final String status) {
        return Stream.of(TimeStatus.values())
                .filter(e -> e.value.equals(status))
                .findFirst()
                .orElse(TimeStatus.TIMELY);
    }

//    public boolean isCheckIn() {
//        return this == LATE;
//    }
//
//    public boolean isCheckOut() {
//        return this == TIMELY;
//    }
//
//    public boolean isValid() {
//        return this != EARLY;
//    }
}
