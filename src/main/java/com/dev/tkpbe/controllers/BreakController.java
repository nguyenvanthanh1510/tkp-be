package com.dev.tkpbe.controllers;

import com.dev.tkpbe.commons.constants.DsdConstant;
import com.dev.tkpbe.models.dtos.Break;
import com.dev.tkpbe.models.dtos.Time;
import com.dev.tkpbe.models.responses.BaseOutput;
import com.dev.tkpbe.services.BreakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/break")
public class BreakController {
    private final BreakService breakService;

    @PostMapping
    public ResponseEntity<BaseOutput<Break>> create(@RequestBody Break breaks){
        if(breaks == null){
            BaseOutput<Break> response =
                    BaseOutput.<Break>builder()
                            .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_BODY))
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
                            .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
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
                            .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
                            .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        breakService.delete(id);
        return ResponseEntity.ok(
                BaseOutput.<String>builder()
                        .data(HttpStatus.OK.toString())
                        .build());
    }
}
