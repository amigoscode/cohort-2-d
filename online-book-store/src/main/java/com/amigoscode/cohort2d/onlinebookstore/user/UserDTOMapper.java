package com.amigoscode.cohort2d.onlinebookstore.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface UserDTOMapper {

    UserDTOMapper INSTANCE = Mappers.getMapper( UserDTOMapper.class );

    UserDto modelToDTO(User user);

    List<UserDto> modelToDTO(Iterable<User> users);

    User dtoToModel(UserDto userDto);

    List<User> dtoToModel(Iterable<UserDto> userDtos);
}
