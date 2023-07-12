package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
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
                userDao.getUserById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("User with id [%s] not found".formatted(id))
                        )
        );
    }
}
