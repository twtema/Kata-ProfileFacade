package org.kata.service.mapper;

import org.kata.dto.IndividualAndContactDto;
import org.kata.dto.IndividualDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IndividualAndContactMapper {
    IndividualAndContactDto toDto(IndividualDto individualDto);

}
