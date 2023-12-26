package com.dev.tkpbe.commons.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Time;
import java.util.stream.Stream;

@Schema(enumAsRef = true)
@RequiredArgsConstructor
@Getter
public enum TimeType {
    CHECK_IN("CHECK_IN"),
    CHECK_OUT("CHECK_OUT"),
    UNDEFINED("UNDEFINED");

    private final String value;

    public static TimeType parse(final String role) {
        return Stream.of(TimeType.values())
                .filter(e -> e.value.equals(role))
                .findFirst()
                .orElse(TimeType.UNDEFINED);
    }

    public boolean isCheckIn() {
        return this == CHECK_IN;
    }

    public boolean isCheckOut() {
        return this == CHECK_OUT;
    }

    public boolean isValid() {
        return this != UNDEFINED;
    }
}
