package com.dev.tkpbe.services.impl;


import java.util.List;
import java.util.stream.Collectors;

import com.dev.tkpbe.commons.constants.DsdConstant;
import com.dev.tkpbe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    com.dev.tkpbe.models.dtos.User loggInUser = userService.findLoggedInfoByEmail(email);

    if (loggInUser != null && !loggInUser.getRoles().isEmpty()) {
      List<GrantedAuthority> authorities =
          loggInUser.getRoles().stream()
              .map(r -> new SimpleGrantedAuthority(r.getType().getValue()))
              .collect(Collectors.toList());

      return new User(loggInUser.getEmail(), loggInUser.getPassword(), authorities);
    } else {
      throw new UsernameNotFoundException(DsdConstant.ERROR.AUTH.NOT_FOUND);
    }
  }
}
