package com.dev.tkpbe.models.entities;

import com.dev.tkpbe.commons.enums.BreakType;
import com.dev.tkpbe.commons.enums.TimeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Entity
@Table(name = "break")
public class BreakEntity extends CommonEntity implements Serializable {
    @Lazy
    @Serial

    private static final long serialVersionUID = -7894854835496777855L;

    @Column(name = "day", unique = true)
    Date day;

    @Column(name = "breaktype", unique = true)
    @Enumerated(EnumType.STRING)
    BreakType breaktype;

    @Column(name = "content")
    String content;


}
