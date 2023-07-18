package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.address.AddressDao;
import com.amigoscode.cohort2d.onlinebookstore.address.AddressDtoMapper;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.RequestValidationException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;
    private final AddressDao addressDao;

    public UserService(UserDao userDao, AddressDao addressDao) {
        this.userDao = userDao;
        this.addressDao = addressDao;
    }

    public UserDto createUser(UserDto userDto) {
        if (userDao.existUserByEmail(userDto.email())) {
            throw new DuplicateResourceException("Email already taken");
        }
        User user = userDao.createUser(UserDTOMapper.INSTANCE.dtoToModel(userDto));
        return UserDTOMapper.INSTANCE.modelToDTO(user);
    }

    public List<UserDto> getUsers() {
        return UserDTOMapper.INSTANCE.modelToDTO(userDao.getUsers());
    }

    public UserDto getUserById(Long id) {
        return UserDTOMapper.INSTANCE.modelToDTO(
                getUser(id)
        );
    }

    public UserDto addAddress(Long userId, UserDto userDto) {
        if (userDto.shippingAddress() == null || userDto.billingAddress() == null) {
            throw new RequestValidationException("address is null");
        }

        var shippingAddress = AddressDtoMapper.INSTANCE.dtoToModel(userDto.shippingAddress());
        var billingAddress = AddressDtoMapper.INSTANCE.dtoToModel(userDto.billingAddress());

        if (shippingAddress.equals(billingAddress)) {
            var createdAddress = addressDao.createAddress(shippingAddress);
            shippingAddress = createdAddress;
            billingAddress = createdAddress;
        }
        var user = getUser(userId);
        user.setBillingAddress(billingAddress);
        user.setShippingAddress(shippingAddress);

        var userResponse = userDao.createUser(user);
        return UserDTOMapper.INSTANCE.modelToDTO(userResponse);
    }

    private User getUser(Long userId) {
        return userDao.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(userId)));
    }


}
