package com.dev.tkpbe.services;

import com.dev.tkpbe.models.dtos.Break;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface BreakService {

    public Page<Break> getByPaging(
            int pageNo, int pageSize, String sortBy, String sortDirection);
    Break getById(Long id);
    Break create(Break breaks);
    Break update(Break breaks);

    void delete(@NonNull Long id);
}
