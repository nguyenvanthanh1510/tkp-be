package com.dev.tkpbe.commons.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.stream.Stream;

@Schema(enumAsRef = true)
@RequiredArgsConstructor
@Getter
public enum TimeType {
    CHECK_IN("CHECK_IN"),
    CHECK_OUT("CHECK_OUT");

    private final String value;

    public static TimeType parse(final String time) {
        return Stream.of(TimeType.values())
                .filter(e -> e.value.equals(time))
                .findFirst()
                .orElse(TimeType.CHECK_IN);
    }

    public boolean isCheckIn() {
        return this == CHECK_IN;
    }

    public boolean isCheckOut() {
        return this == CHECK_OUT;
    }

}
