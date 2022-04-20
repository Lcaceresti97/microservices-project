package com.sti.usuarioservice.service;

import com.sti.usuarioservice.dto.CarDto;
import com.sti.usuarioservice.dto.UserDto;
import com.sti.usuarioservice.exception.UserNotFoundException;
import com.sti.usuarioservice.model.Car;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Service interface for User entity crud operations.
 * @author Laurent Caceres
 * @version 1.0.0
 */
public interface UserService {


    /**
     * Saves given User into DB.
     * @param userDto User
     */
    UserDto saveUser(UserDto userDto);

    /**
     * Find a User by its ID.
     * @param userId String
     * @return user userDto
     */
    UserDto findUserById(final String userId) throws UserNotFoundException;

    /**
     * Return a page of sorted user.
     * @param userName Users names to sort by.
     * @param page Page number to query by.
     * @param size Page size to query by.
     * @param sort Extra sort params to sort by.
     * @return PageResponseDto User.
     */
    Page<UserDto> findPaginatedSortedUser(String userName, int page, int size, String[] sort);


    /**
     * Find a Car User by its ID.
     * @param userId String
     * @return car carDto
     */
    Map<String, Object> getCarsByUserId(String userId) throws UserNotFoundException;

    /**
     * Saves given Car by User into DB.
     * @param carDto Car
     */
    CarDto saveCarByUser(String userId, CarDto carDto);

    /**
     * Delete user by its ID.
     * @param userId
     */
    void deleteUserById(final String userId);


}
