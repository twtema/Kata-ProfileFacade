package org.kata.service.mapper;

import org.kata.dto.Test1Dto;
import org.kata.dto.Test2Dto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TestMapper {
    Test2Dto toDto(Test1Dto test1Dto);
}
