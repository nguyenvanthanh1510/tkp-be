package com.dev.tkpbe.services;

import com.dev.tkpbe.models.dtos.Break;
import lombok.NonNull;

public interface BreakService {

    Break create(Break breaks);
    Break update(Break breaks);

    void delete(@NonNull Long id);
}
