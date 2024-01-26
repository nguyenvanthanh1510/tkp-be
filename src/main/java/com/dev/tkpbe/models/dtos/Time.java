package com.dev.tkpbe.models.dtos;

import com.dev.tkpbe.commons.enums.TimeStatus;
import com.dev.tkpbe.commons.enums.TimeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Time {

    Long id;

    TimeType type;

    Date time;

    TimeStatus status;

    User user;

}
