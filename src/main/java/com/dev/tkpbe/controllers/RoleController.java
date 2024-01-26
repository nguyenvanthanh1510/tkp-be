package com.dev.tkpbe.controllers;

import com.dev.tkpbe.commons.constants.TkpConstant;
import com.dev.tkpbe.models.dtos.Role;
import com.dev.tkpbe.models.responses.BaseOutput;
import com.dev.tkpbe.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
  private final RoleService roleService;

  @Operation(
      summary = "Get all user's Role with pagination",
      description = "Returns all user's Role with pagination")
  @ApiResponses(@ApiResponse(responseCode = "200", description = "Successfully retrieved"))
  @GetMapping("")
  protected ResponseEntity<BaseOutput<List<Role>>> getByPaging(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false, defaultValue = "id") String sortBy) {
    Page<Role> rolesPage = roleService.getByPaging(page, size, sortBy);

    BaseOutput<List<Role>> response =
        BaseOutput.<List<Role>>builder()
            .message(HttpStatus.OK.toString())
            .totalPages(rolesPage.getTotalPages())
            .currentPage(page)
            .pageSize(size)
            .total(rolesPage.getTotalElements())
            .data(rolesPage.getContent())
            .build();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  protected ResponseEntity<BaseOutput<Role>> getById(
      @PathVariable("id")  Long id) {
    if (id <= 0) {
      BaseOutput<Role> response =
          BaseOutput.<Role>builder()
              .errors(List.of(TkpConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    Role role = roleService.getById(id);

    BaseOutput<Role> response =
        BaseOutput.<Role>builder()
            .message(HttpStatus.OK.toString())
            .data(role)
            .build();
    return ResponseEntity.ok(response);
  }


  @PostMapping
  protected ResponseEntity<BaseOutput<Role>> create(
      @RequestBody  Role role) {

    if (role == null) {
      BaseOutput<Role> response =
          BaseOutput.<Role>builder()
              .errors(List.of(TkpConstant.ERROR.REQUEST.INVALID_BODY))
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    Role createdRole = roleService.create(role);
    BaseOutput<Role> response =
        BaseOutput.<Role>builder()
            .message(HttpStatus.OK.toString())
            .data(createdRole)
            .build();
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  protected ResponseEntity<BaseOutput<Role>> update(
      @PathVariable("id")  Long id,
      @RequestBody  Role role) {
    if (id <= 0) {
      BaseOutput<Role> response =
          BaseOutput.<Role>builder()
              .errors(List.of(TkpConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    role.setId(id);
    Role createdRole = roleService.update(role);
    BaseOutput<Role> response =
        BaseOutput.<Role>builder()
            .message(HttpStatus.OK.toString())
            .data(createdRole)
            .build();
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  protected ResponseEntity<BaseOutput<String>> delete(
      @PathVariable("id")  Long id) {
    if (id <= 0) {
      BaseOutput<String> response =
          BaseOutput.<String>builder()
              .errors(List.of(TkpConstant.ERROR.REQUEST.INVALID_PATH_VARIABLE))
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    roleService.delete(id);
    return ResponseEntity.ok(
        BaseOutput.<String>builder()
            .data(HttpStatus.OK.toString())
            .build());
  }
}
