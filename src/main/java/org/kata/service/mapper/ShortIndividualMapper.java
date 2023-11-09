package org.kata.service.mapper;

import org.kata.dto.IndividualDto;
import org.kata.dto.ShortIndividualDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShortIndividualMapper {

    ShortIndividualDto toDto(IndividualDto individualDto);

    IndividualDto toDto(ShortIndividualDto shortIndividualDto);

}