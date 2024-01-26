package com.dev.tkpbe.commons.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Schema(enumAsRef = true)
@RequiredArgsConstructor
@Getter
public enum BreakStatus {

    NEW("NEW"),
    APPROVE("APPROVE");


    private final String value;

    public static BreakStatus parse(final String status) {
        return Stream.of(BreakStatus.values())
                .filter(e -> e.value.equals(status))
                .findFirst()
                .orElse(BreakStatus.NEW);
    }
}
