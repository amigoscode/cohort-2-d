package com.amigoscode.cohort2d.onlinebookstore.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDTOMapper {

    UserDTOMapper INSTANCE = Mappers.getMapper( UserDTOMapper.class );

    @Mapping(target = "shippingAddress", source = "shippingAddress")
    @Mapping(target = "billingAddress", source = "billingAddress")
    UserDto modelToDTO(User user);

    List<UserDto> modelToDTO(Iterable<User> users);

    @Mapping(target = "shippingAddress", source = "shippingAddress")
    @Mapping(target = "billingAddress", source = "billingAddress")
    User dtoToModel(UserDto userDto);

    List<User> dtoToModel(Iterable<UserDto> userDtos);
}
