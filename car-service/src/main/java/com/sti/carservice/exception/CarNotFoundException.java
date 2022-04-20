package com.sti.carservice.exception;


import com.sti.carservice.model.Car;

/**
 * Car 404 status exception.
 * @author Laurent Caceres
 */
public class CarNotFoundException extends ResourceNotFoundException {

    /**
     *
     * @param field
     * @param fieldValue
     * @return
     */
    public static ResourceNotFoundException
            buildCarNotFoundExceptionForField(String field, String fieldValue){
        return resourceNotFoundExceptionOf(Car.class, field, fieldValue);
    }

    /**
     *
     * @param carId
     * @return
     */
    public static ResourceNotFoundException
            buildCarNotFoundExceptionForId(String carId){
        return resourceNotFoundExceptionOf(Car.class, "carId", carId);
    }

    /**
     *
     * @param searchParams
     * @return ResourceNotFoundException instance
     */
    public static ResourceNotFoundException buildCarNotFoundException(String... searchParams){
        return resourceNotFoundExceptionOf(Car.class, searchParams);
    }

}