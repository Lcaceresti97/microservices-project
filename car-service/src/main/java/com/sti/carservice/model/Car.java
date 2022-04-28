package com.sti.carservice.model;

import com.sti.carservice.model.status.ModelStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * Car class to represent Car entity.
 *
 * @author Laurent Caceres
 * @version 1.0.0
 */
@Entity
@Table(name = "t_cars")
@Builder
@Getter
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

    /**
     * Adds fields which are not populated by Car DTO.
     *
     * @return
     */
    public static Car buildFromDto(Car car) {
        car.setCarId(UUID.randomUUID().toString());
        car.setCarStatus(ModelStatus.ACTIVE);
        return car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        Car car = (Car) o;
        return this.carId == car.carId
                && (this.carMake.equals(car.carMake))
                && (this.carModel.equals(car.carModel));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.carModel == null ? 0 : this.carModel.hashCode());
        hash = 31 * hash + (this.carId == null ? 0 : this.carId.hashCode());
        hash = 31 * hash + (this.carMake == null ? 0 : this.carMake.hashCode());
        return hash;
    }

    private void setCarId(final String carId) {
        this.carId = carId;
    }

    public void setCarStatus(ModelStatus modelStatus) {
        this.carStatus = modelStatus;
    }
}
