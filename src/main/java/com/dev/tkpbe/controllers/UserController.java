package com.dev.tkpbe.controllers;

import com.dev.tkpbe.commons.constants.DsdConstant;
import com.dev.tkpbe.models.dtos.User;
import com.dev.tkpbe.models.responses.BaseOutput;
import com.dev.tkpbe.services.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;


  @GetMapping("")
  public ResponseEntity<BaseOutput<List<User>>> getAllByPaging(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false, defaultValue = "id") String sortBy,
      @RequestParam(required = false, defaultValue = "ASC") String sortDirection) {
    Page<User> userPage = userService.getByPaging(page, size, sortBy, sortDirection);
    BaseOutput<List<User>> response =
        BaseOutput.<List<User>>builder()
            .message(HttpStatus.OK.toString())
            .totalPages(userPage.getTotalPages())
            .currentPage(page)
            .pageSize(size)
            .total(userPage.getTotalElements())
            .data(userPage.getContent())
            .build();
    return ResponseEntity.ok(response);
  }


  @GetMapping("/{id}")
  public ResponseEntity<BaseOutput<User>> getById(@PathVariable("id")  Long id) {
    if (id == null) {
      BaseOutput<User> response =
          BaseOutput.<User>builder()
              .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    User user = userService.getById(id);

    BaseOutput<User> response =
        BaseOutput.<User>builder()
            .message(HttpStatus.OK.toString())
            .data(user)
            .build();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<BaseOutput<User>> getByEmail(@PathVariable("email")  String email) {
    if (StringUtils.isAllBlank(email)) {
      BaseOutput<User> response =
          BaseOutput.<User>builder()
              .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    User user = userService.getByEmail(email);

    BaseOutput<User> response =
        BaseOutput.<User>builder()
            .message(HttpStatus.OK.toString())
            .data(user)
            .build();
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<BaseOutput<User>> create(@RequestBody  User user) {

    if (user == null) {
      BaseOutput<User> response =
          BaseOutput.<User>builder()
                  .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_BODY))
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    User createdUser = userService.create(user);
    BaseOutput<User> response =
        BaseOutput.<User>builder()
            .message(HttpStatus.OK.toString())
            .data(createdUser)
            .build();
    return ResponseEntity.ok(response);
  }


  @PutMapping("/{id}")
  public ResponseEntity<BaseOutput<User>> update(@PathVariable("id")  Long id,
      @RequestBody  User user) {
    if (id == null) {
      BaseOutput<User> response =
          BaseOutput.<User>builder()
                  .errors(List.of(DsdConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    user.setId(id);
    User createdBid = userService.update(user);
    BaseOutput<User> response =
        BaseOutput.<User>builder()
            .message(HttpStatus.OK.toString())
            .data(createdBid)
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
    userService.delete(id);
    return ResponseEntity.ok(
        BaseOutput.<String>builder()
            .data(HttpStatus.OK.toString())
            .build());
  }

}
