package com.sti.carservice.repository;

import com.sti.carservice.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Car entity.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@Repository
public interface CarRepository extends JpaRepository<Car, String> {

    /**
     * Find Paginated car by model.
     * @param carModel
     * @param pageable
     * @return
     */
    Page<Car> findByCarModelContaining (String carModel, Pageable pageable);

    /**
     * Find car by user Id.
     * @param userId
     * @return
     */
    List<Car> findCarsByUserId(String userId);
}
