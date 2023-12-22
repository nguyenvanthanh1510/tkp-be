package com.dev.tkpbe.models.responses;

import com.dev.tkpbe.commons.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseOutput<T> {
  List<String> errors;
  String message;
  Integer currentPage;
  Integer pageSize;
  Integer totalPages;
  Long total;
  ResponseStatus status;
  T data;
}
