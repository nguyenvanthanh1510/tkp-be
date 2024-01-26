package com.dev.tkpbe.components;

import com.dev.tkpbe.models.dtos.Break;
import com.dev.tkpbe.models.dtos.Time;
import com.dev.tkpbe.models.entities.BreakEntity;
import com.dev.tkpbe.models.entities.TimeEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BreakMapper {
    public Break toDTO(BreakEntity entity) {
        return Optional.ofNullable(entity).map(this::convertToDto).orElse(null);
    }

    public BreakEntity toEntity(Break dto) {
        return Optional.ofNullable(dto)
                .map(
                        e ->
                                BreakEntity.builder()
                                        .id(e.getId())
                                        .type(e.getType())
                                        .day(e.getDay())
                                        .content(e.getContent())
                                        .build())
                .orElse(null);
    }
    private Break convertToDto(BreakEntity entity) {
        return Optional.ofNullable(entity)
                .map(
                        e ->
                                Break.builder()
                                        .id(e.getId())
                                        .day(e.getDay())
                                        .type(e.getType())
                                        .content(e.getContent())
                                        .build())
                .orElse(null);
    }
}
