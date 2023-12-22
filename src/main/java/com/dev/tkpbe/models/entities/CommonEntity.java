package com.dev.tkpbe.models.entities;

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
@EqualsAndHashCode
@MappedSuperclass
public class CommonEntity implements Serializable {

  @Serial private static final long serialVersionUID = -6137868994640459777L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  Long id;
}
