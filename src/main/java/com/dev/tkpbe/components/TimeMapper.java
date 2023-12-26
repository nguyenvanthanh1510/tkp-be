package com.dev.tkpbe.components;

import com.dev.tkpbe.models.dtos.Time;
import com.dev.tkpbe.models.dtos.User;
import com.dev.tkpbe.models.entities.TimeEntity;
import com.dev.tkpbe.models.entities.UserEntity;
import com.dev.tkpbe.models.entities.UserRoleMapEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TimeMapper {
    public Time toDTO(TimeEntity entity) {
        return Optional.ofNullable(entity).map(this::convertToDto).orElse(null);
    }

    public TimeEntity toEntity(Time dto) {
        return Optional.ofNullable(dto)
                .map(
                        e ->
                                TimeEntity.builder()
                                        .id(e.getId())
                                        .type(e.getType())
                                        .time(e.getTime())
                                        .build())
                .orElse(null);
    }
    private Time convertToDto(TimeEntity entity) {
        return Optional.ofNullable(entity)
                .map(
                        e ->
                                Time.builder()
                                        .id(e.getId())
                                        .type(e.getType())
                                        .time(e.getTime())
                                        .build())
                .orElse(null);
    }
}
