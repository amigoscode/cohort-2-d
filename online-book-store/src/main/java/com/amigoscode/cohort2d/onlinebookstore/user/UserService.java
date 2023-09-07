package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.address.AddressDto;
import com.amigoscode.cohort2d.onlinebookstore.address.AddressDtoMapper;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserDto userDto) {
        if (userDao.existUserByEmail(userDto.email())) {
            throw new DuplicateResourceException("Email already taken");
        }
        User userRequest = UserDTOMapper.INSTANCE.dtoToModel(userDto);
        userRequest.setPassword(passwordEncoder.encode(userDto.password()));
        User user = userDao.saveUser(userRequest);
        return UserDTOMapper.INSTANCE.modelToDTO(user);
    }

    public List<UserDto> getUsers() {
        return UserDTOMapper.INSTANCE.modelToDTO(userDao.getUsers());
    }

    @Transactional
    public UserDto getUserById(Long id) {
        return UserDTOMapper.INSTANCE.modelToDTO(
                getUser(id)
        );
    }

    public UserDto addAddress(Long userId, AddressDto addressDto) {
        var user = getUser(userId);

        user.addAddress(AddressDtoMapper.INSTANCE.dtoToModel(addressDto));

        var userResponse = userDao.saveUser(user);
        return UserDTOMapper.INSTANCE.modelToDTO(userResponse);
    }

    private User getUser(Long userId) {
        return userDao.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(userId)));
    }


    public List<AddressDto> getUserAddresses(long userId) {
        var user = userDao.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(userId)));
        return AddressDtoMapper.INSTANCE.modelToDto(user.getAddresses());
    }
}
