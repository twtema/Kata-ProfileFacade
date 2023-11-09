package org.kata.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kata.dto.IndividualAndContactDto;
import org.kata.dto.IndividualAndRfPassportDto;
import org.kata.dto.IndividualDto;
import org.kata.dto.ShortIndividualDto;
import org.kata.dto.Test1Dto;
import org.kata.dto.Test2Dto;
import org.kata.feignclient.ProfileServiceFeignClient;
import org.kata.service.IndividualService;
import org.kata.service.mapper.IndividualAndContactMapper;
import org.kata.service.mapper.IndividualAndRfPassportMapper;
import org.kata.service.mapper.ShortIndividualMapper;
import org.kata.service.mapper.TestMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndividualServiceImp implements IndividualService {

    private final ProfileServiceFeignClient profileServiceFeignClient;

    private final TestMapper testMapper;
    private final ShortIndividualMapper shortIndividualMapper;
    private final IndividualAndContactMapper individualAndContactMapper;
    private final IndividualAndRfPassportMapper individualAndRfPassportMapper;

    @Override
    public Test2Dto getTest() {
        Test1Dto test1Dto = profileServiceFeignClient.getTest();
        System.out.println("1:" +test1Dto);
        Test2Dto test2Dto = testMapper.toDto(test1Dto);
        System.out.println("2:" +test2Dto);
        return test2Dto;
    }

//    @Override
//    public ShortIndividualDto getShortIndividualInformation(String icp) {
//        IndividualDto individualDto = profileServiceFeignClient.getIndividual(icp);
//        return shortIndividualMapper.toDto(individualDto);
//    }
//
//    @Override
//    public IndividualDto getFullIndividualInformation(String icp) {
//        return profileServiceFeignClient.getIndividual(icp);
//    }
//
//    @Override
//    public IndividualAndContactDto getIndividualAndContactInformation(String icp) {
//        IndividualDto individualDto = profileServiceFeignClient.getIndividual(icp);
//        return individualAndContactMapper.toDto(individualDto);
//    }
//
//    @Override
//    public IndividualAndRfPassportDto getIndividualAndRfPassportInformation(String icp) {
//        IndividualDto individualDto = profileServiceFeignClient.getIndividual(icp);
//        return individualAndRfPassportMapper.toDto(individualDto);
//    }
}
