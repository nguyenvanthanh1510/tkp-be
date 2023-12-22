package com.dev.tkpbe.models.entities;

import com.dev.tkpbe.commons.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role")
public class RoleEntity extends CommonEntity implements Serializable {

  @Serial private static final long serialVersionUID = -6426442714755236260L;

  @Column(name = "name")
  String name;

  @Column(name = "type", unique = true)
  @Enumerated(EnumType.STRING)
  RoleType type;

  @OneToMany(mappedBy = "role")
  List<UserRoleMapEntity> userRoleMap;
}
