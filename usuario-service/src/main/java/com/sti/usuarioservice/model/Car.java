package com.sti.usuarioservice.model;

import com.sti.usuarioservice.model.status.ModelStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * Car class to represent Car entity.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@Entity
@Table(name = "t_cars")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @Column(name = "car_id", nullable = false, unique = true, length = 64)
    private String carId;

    @Column(name = "car_make", nullable = false)
    private String carMake;

    @Column(name = "car_model", nullable = false)
    private String carModel;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "car_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ModelStatus carStatus;

}
