package com.dev.tkpbe.services;

import com.dev.tkpbe.models.dtos.User;
import com.dev.tkpbe.models.dtos.UserRoleMap;
import java.util.List;

public interface UserRoleMapService {
  List<UserRoleMap> findByUser(User user);

  void setUserRoleForUser(Long userId);

  void setAdminRoleForUser(Long userId);
}
