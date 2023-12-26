package com.dev.tkpbe.services;

import com.dev.tkpbe.models.dtos.Time;
import com.dev.tkpbe.models.dtos.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface TimeService {

    Page<Time> getByPaging(
            int pageNo, int pageSize, String sortBy, String sortDirection);
    Time getById(Long id);
    Time create(Time time);
    Time update(Time time);
    void delete( Long id);
}
