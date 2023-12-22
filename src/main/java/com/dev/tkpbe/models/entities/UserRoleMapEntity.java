package com.dev.tkpbe.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
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
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"user", "role"})
@Entity
@Table(name = "user_role_map")
public class UserRoleMapEntity extends CommonEntity implements Serializable {
  @Serial private static final long serialVersionUID = 995486484416424681L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  RoleEntity role;
}
