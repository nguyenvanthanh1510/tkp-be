package com.dev.tkpbe.models.entities;

import com.dev.tkpbe.commons.enums.BreakStatus;
import com.dev.tkpbe.commons.enums.BreakType;
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
@Table(name = "break")
public class BreakEntity extends CommonEntity implements Serializable {
    @Lazy
    @Serial
    private static final long serialVersionUID = -7894854835496777855L;

    @Column(name = "day")
    Date day;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    BreakType type;

    @Column(name = "content")
    String content;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    BreakStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    UserEntity user;

}
