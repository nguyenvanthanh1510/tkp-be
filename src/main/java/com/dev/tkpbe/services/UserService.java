package com.dev.tkpbe.services;

import java.util.List;

import com.dev.tkpbe.models.dtos.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface UserService {

  String getAuthenticatedUserEmail();

  Page<User> getByPaging(
      int pageNo, int pageSize, String sortBy, String sortDirection);

  User getById(Long id);

  User create(User user);

  User update(User user);

  void delete(@NonNull Long id);

  User findLoggedInfoByEmail(String email);

  User createAdmin(User user);

  User getByEmail(@NonNull String email);



}
