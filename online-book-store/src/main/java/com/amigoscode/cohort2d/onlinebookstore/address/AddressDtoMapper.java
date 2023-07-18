package com.amigoscode.cohort2d.onlinebookstore.address;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
)
public interface AddressDtoMapper {
    AddressDtoMapper INSTANCE = Mappers.getMapper(AddressDtoMapper.class);

    AddressDto modelToDto(Address address);

    List<AddressDto> modelToDto(Iterable<Address> addresses);

    Address dtoToModel(AddressDto addressDto);

    List<Address> dtoToModel(Iterable<AddressDto> addressDtos);
}
