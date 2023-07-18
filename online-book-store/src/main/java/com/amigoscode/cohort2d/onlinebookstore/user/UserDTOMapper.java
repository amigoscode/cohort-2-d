package com.amigoscode.cohort2d.onlinebookstore.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDTOMapper {

    UserDTOMapper INSTANCE = Mappers.getMapper( UserDTOMapper.class );

    @Mapping(target = "addresses", ignore = true)
    UserDto modelToDTO(User user);

    List<UserDto> modelToDTO(Iterable<User> users);

    User dtoToModel(UserDto userDto);

    List<User> dtoToModel(Iterable<UserDto> userDtos);
}
