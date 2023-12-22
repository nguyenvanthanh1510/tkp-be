package com.dev.tkpbe.controllers;

import com.dev.tkpbe.components.JwtUtil;
import com.dev.tkpbe.configs.exceptions.JwtAuthenticationException;
import com.dev.tkpbe.models.requests.AuthRequest;
import com.dev.tkpbe.models.responses.BaseOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<BaseOutput<String>> login(@RequestBody AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    } catch (BadCredentialsException e) {
      throw new JwtAuthenticationException("error.auth.invalid.credentials");
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
    String jwt = jwtUtil.generateToken(userDetails);

    BaseOutput<String> response =
        BaseOutput.<String>builder()
            .message(HttpStatus.OK.toString())
            .data(jwt)
            .build();

    return ResponseEntity.ok(response);
  }
}
