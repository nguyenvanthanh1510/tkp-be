package com.dev.tkpbe.commons.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Schema(enumAsRef = true)
@RequiredArgsConstructor
@Getter
public enum BreakType {
    XIN_DEN_MUON("XIN_DEN_MUON"),
    XIN_VE_SOM("XIN_VE_SOM"),
    XIN_NGHI("XIN_NGHI"),
    UNDEFINED("UNDEFINED");

    private final String value;

    public static BreakType parse(final  String role){
        return Stream.of(BreakType.values())
                .filter(e -> e.value.equals(role))
                .findFirst()
                .orElse(BreakType.UNDEFINED);
    }
    public boolean isXinDenMuon() {
        return this == XIN_DEN_MUON;
    }

    public boolean isXinVeSom() {
        return this == XIN_VE_SOM;
    }

    public boolean isXinNghi() {
        return this == XIN_NGHI;
    }

    public boolean isValid() {
        return this != UNDEFINED;
    }
}
