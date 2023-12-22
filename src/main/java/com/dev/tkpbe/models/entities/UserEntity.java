package com.dev.tkpbe.models.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, exclude = "userRoleMap")
@Entity
@Table(name = "users")
public class UserEntity extends CommonEntity implements Serializable {

  @Serial private static final long serialVersionUID = -5119400827834851778L;

  @Column(name = "username", unique = true)
  String userName;

  @Column(name = "email", unique = true)
  String email;

  @Column(name = "password")
  String password;

  @Column(name = "phone")
  String phone;

  @Column(name = "first_name")
  String firstName;

  @Column(name = "last_name")
  String lastName;

  @OneToMany(mappedBy = "user")
  List<UserRoleMapEntity> userRoleMap;

}
