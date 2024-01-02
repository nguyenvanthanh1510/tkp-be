package com.dev.tkpbe.controllers;

import com.dev.tkpbe.commons.constants.DsdConstant;
import com.dev.tkpbe.models.dtos.Time;
import com.dev.tkpbe.models.dtos.User;
import com.dev.tkpbe.models.responses.BaseOutput;
import com.dev.tkpbe.services.TimeService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/time")
public class TimeController {
    private final TimeService timeService;

    @GetMapping("")
    public ResponseEntity<BaseOutput<List<Time>>> getAllByPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection) {
        Page<Time> timePage = timeService.getByPaging(page, size, sortBy, sortDirection);
        BaseOutput<List<Time>> response =
                BaseOutput.<List<Time>>builder()
                        .message(HttpStatus.OK.toString())
                        .totalPages(timePage.getTotalPages())
                        .currentPage(page)
                        .pageSize(size)
                        .total(timePage.getTotalElements())
                        .data(timePage.getContent())
                        .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseOutput<Time>> getById(@PathVariable("id")  Long id) {
        if (id == null) {
            BaseOutput<Time> response =
                    BaseOutput.<Time>builder()
                            .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
                            .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Time time = timeService.getById(id);

        BaseOutput<Time> response =
                BaseOutput.<Time>builder()
                        .message(HttpStatus.OK.toString())
                        .data(time)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseOutput<Time>> create(@RequestBody Time time){
        if(time == null){
            BaseOutput<Time> response =
            BaseOutput.<Time>builder()
                    .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_BODY))
                    .build();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Time createTime = timeService.create(time);
        BaseOutput<Time> response =
                BaseOutput.<Time>builder()
                        .message(HttpStatus.OK.toString())
                        .data(createTime)
                        .build();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseOutput<Time>> update(@PathVariable("id") Long id, @RequestBody Time time){
        if(id == null){
            BaseOutput<Time> response =
                    BaseOutput.<Time>builder()
                            .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
                            .build();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        time.setId(id);
        Time updateId = timeService.update(time);
        BaseOutput<Time> response =
                BaseOutput.<Time>builder()
                        .message(HttpStatus.OK.toString())
                        .data(updateId)
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseOutput<String>> delete(@PathVariable("id")  Long id) {
        if (id <= 0) {
            BaseOutput<String> response =
                    BaseOutput.<String>builder()
                            .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
                            .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        timeService.delete(id);
        return ResponseEntity.ok(
                BaseOutput.<String>builder()
                        .data(HttpStatus.OK.toString())
                        .build());
    }

    @PostMapping("/check-in")
    public ResponseEntity<BaseOutput<Time>> checkIn( Time time){
        if(time == null){
            BaseOutput<Time> response =
                    BaseOutput.<Time>builder()
                            .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_BODY))
                            .build();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Time createTime = timeService.checkIn(time);
        BaseOutput<Time> response =
                BaseOutput.<Time>builder()
                        .message(HttpStatus.OK.toString())
                        .data(createTime)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-out")
    public ResponseEntity<BaseOutput<Time>> checkOut( Time time){
        if(time == null){
            BaseOutput<Time> response =
                    BaseOutput.<Time>builder()
                            .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_BODY))
                            .build();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Time createTime = timeService.checkOut(time);
        BaseOutput<Time> response =
                BaseOutput.<Time>builder()
                        .message(HttpStatus.OK.toString())
                        .data(createTime)
                        .build();
        return ResponseEntity.ok(response);
    }
}
