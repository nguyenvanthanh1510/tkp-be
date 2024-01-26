package com.dev.tkpbe.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardResponse {
    Long totalGoLate;
    Long totalArriveEarly;
}
