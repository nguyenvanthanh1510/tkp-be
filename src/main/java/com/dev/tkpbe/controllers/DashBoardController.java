package com.dev.tkpbe.controllers;

import com.dev.tkpbe.models.responses.BaseOutput;
import com.dev.tkpbe.models.responses.DashBoardResponse;
import com.dev.tkpbe.services.DashBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dash-board")
public class DashBoardController {

    @Lazy private final DashBoardService dashBoardService;

    @GetMapping("")
    protected ResponseEntity<BaseOutput<DashBoardResponse>> getDashBoardStatus() {
        DashBoardResponse dashBoardResponse = dashBoardService.getDashBoard();
        BaseOutput<DashBoardResponse> response =
                BaseOutput.<DashBoardResponse>builder()
                        .message(HttpStatus.OK.toString())
                        .data(dashBoardResponse)
                        .build();
        return ResponseEntity.ok(response);
    }
}
