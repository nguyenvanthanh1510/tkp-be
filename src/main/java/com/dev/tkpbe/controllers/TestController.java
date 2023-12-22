package com.dev.tkpbe.controllers;


import com.dev.tkpbe.models.responses.BaseOutput;
import com.dev.tkpbe.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

  private final UserService userService;

  @GetMapping("/endpoint")
  public ResponseEntity<BaseOutput<String>> test() {
    String loggedEmail = userService.getAuthenticatedUserEmail();
    return ResponseEntity.ok(
        BaseOutput.<String>builder()
            .data("admin endpoint: " + loggedEmail)
            .build());
  }
}
