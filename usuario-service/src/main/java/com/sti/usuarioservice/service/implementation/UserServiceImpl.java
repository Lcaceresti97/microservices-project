package com.sti.usuarioservice.service.implementation;

import com.sti.usuarioservice.dto.CarDto;
import com.sti.usuarioservice.dto.UserDto;
import com.sti.usuarioservice.exception.UserNotFoundException;
import com.sti.usuarioservice.feignclients.CarFeignClient;
import com.sti.usuarioservice.model.Car;
import com.sti.usuarioservice.model.User;
import com.sti.usuarioservice.model.mapper.CarMapper;
import com.sti.usuarioservice.model.mapper.UserMapper;
import com.sti.usuarioservice.model.status.ModelStatus;
import com.sti.usuarioservice.repository.UserRepository;
import com.sti.usuarioservice.service.UserService;
import com.sti.usuarioservice.utils.SortingPagingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for User entity.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private  final UserMapper userMapper;

    private final CarMapper carMapper;

    private final SortingPagingUtils sortingPagingUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Override
    public UserDto findUserById(final String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.buildUserNotFoundExceptionForId(userId));
        return userMapper.userToDto(isActiveUser(user,"userId", userId));
    }

    @Override
    public Page<UserDto> findPaginatedSortedUser(final String userName,final int page, final int size, final String[] sort) {
        List<Sort.Order> orderList = sortingPagingUtils.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderList));
        List<UserDto> userDtos;
        if(userName == null){
            userDtos = userMapper.userToDto(userRepository.findAll(pageable).toList());
        } else {
            userDtos = userMapper.userToDto(userRepository.findByUserNameContaining(userName, pageable).toList());
        }
        return new PageImpl<>(userDtos);
    }

    @Override
    public Map<String, Object> getCarsByUserId(String userId) {
        Map<String, Object> getCars = new HashMap<>();
        User user = userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.buildUserNotFoundExceptionForId(userId));

        if(user == null){
            getCars.put("Message:", "user does not exist");
            return getCars;
        }

        getCars.put("User:", user);
        List<CarDto> carDtoList = carFeignClient.getCarsByUser(userId);
        if (carDtoList.isEmpty()){
            getCars.put("Cars", "User does not exist");
        } else {
            getCars.put("Cars:", carDtoList);
        }
        return getCars;
    }

    @Override
    public CarDto saveCarByUser(String userId, CarDto carDto) {
        carDto.setUserId(userId);
        CarDto savedCar = carFeignClient.saveCar(carDto);
        return savedCar;
    }


    @Override
    public UserDto saveUser(final UserDto userDto) {
        User user = User.buildFromDto(this.userMapper.dtoToUser(userDto));
        this.userRepository.save(user);
        return userMapper.userToDto(user);
    }

    @Override
    public void deleteUserById(final String userId) {
        User user = userMapper.dtoToUser(findUserById(userId));
        user.setUserStatus(ModelStatus.INACTIVE);
        userRepository.save(user);
    }

    /**
     * Return User if status code is ACTIVE.
     * @param user User
     * @param queryField String
     * @param queryFieldValue String
     * @return User
     * @throws UserNotFoundException ex
     */
    private User isActiveUser(User user, String queryField, String queryFieldValue){
        if(user.getUserStatus().getStatusCode() == 0){
            return user;
        }
        throw UserNotFoundException
                .buildUserNotFoundExceptionForField(queryField, queryFieldValue);
    }
}
