package org.kata.service.mapper;

import org.kata.dto.IndividualAndRfPassportDto;
import org.kata.dto.IndividualDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IndividualAndRfPassportMapper {

    IndividualAndRfPassportDto toDto(IndividualDto individualDto);

}
