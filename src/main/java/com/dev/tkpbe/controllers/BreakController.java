package com.dev.tkpbe.controllers;

import com.dev.tkpbe.commons.constants.TkpConstant;
import com.dev.tkpbe.models.dtos.Break;
import com.dev.tkpbe.models.responses.BaseOutput;
import com.dev.tkpbe.services.BreakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/break")
public class BreakController {
    @Lazy private final BreakService breakService;

    @GetMapping("")
    public ResponseEntity<BaseOutput<List<Break>>> getAllByPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection) {
        Page<Break> breakPage = breakService.getByPaging(page, size, sortBy, sortDirection);
        BaseOutput<List<Break>> response =
                BaseOutput.<List<Break>>builder()
                        .message(HttpStatus.OK.toString())
                        .totalPages(breakPage.getTotalPages())
                        .currentPage(page)
                        .pageSize(size)
                        .total(breakPage.getTotalElements())
                        .data(breakPage.getContent())
                        .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseOutput<Break>> getById(@PathVariable("id")  Long id) {
        if (id == null) {
            BaseOutput<Break> response =
                    BaseOutput.<Break>builder()
                            .errors(List.of(TkpConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
                            .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Break breaks = breakService.getById(id);

        BaseOutput<Break> response =
                BaseOutput.<Break>builder()
                        .message(HttpStatus.OK.toString())
                        .data(breaks)
                        .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<BaseOutput<Break>> create(@RequestBody Break breaks){
        if(breaks == null){
            BaseOutput<Break> response =
                    BaseOutput.<Break>builder()
                            .errors(List.of(TkpConstant.ERROR.REQUEST.INVALID_BODY))
                            .build();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Break createBreak = breakService.create(breaks);
        BaseOutput<Break> response =
                BaseOutput.<Break>builder()
                        .message(HttpStatus.OK.toString())
                        .data(createBreak)
                        .build();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseOutput<Break>> update(@PathVariable("id") Long id, @RequestBody Break breaks){
        if(id == null){
            BaseOutput<Break> response =
                    BaseOutput.<Break>builder()
                            .errors(List.of(TkpConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
                            .build();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        breaks.setId(id);
        Break updateId = breakService.update(breaks);
        BaseOutput<Break> response =
                BaseOutput.<Break>builder()
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
                            .errors(List.of(TkpConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
                            .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        breakService.delete(id);
        return ResponseEntity.ok(
                BaseOutput.<String>builder()
                        .data(HttpStatus.OK.toString())
                        .build());
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<BaseOutput<Break>> updateStatus(@PathVariable("id") Long id){
        if(id == null){
            BaseOutput<Break> response =
                    BaseOutput.<Break>builder()
                            .errors(List.of(TkpConstant.ERROR.REQUEST.INVALID))
                            .build();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Break updateId = breakService.updateStatus(id);
        BaseOutput<Break> response =
                BaseOutput.<Break>builder()
                        .message(HttpStatus.OK.toString())
                        .data(updateId)
                        .build();
        return ResponseEntity.ok(response);
    }
}
