package com.dev.tkpbe.models.entities;

import com.dev.tkpbe.commons.enums.TimeStatus;
import com.dev.tkpbe.commons.enums.TimeType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.context.annotation.Lazy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "time")
public class TimeEntity extends CommonEntity implements Serializable {

    @Lazy
    @Serial
    private static final long serialVersionUID = -7894854835496777855L;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    TimeType type;

    @Column(name = "time")
    Date time;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    TimeStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    UserEntity user;
}
