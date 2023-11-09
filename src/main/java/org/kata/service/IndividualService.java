package org.kata.service;

import org.kata.dto.IndividualAndContactDto;
import org.kata.dto.IndividualAndRfPassportDto;
import org.kata.dto.IndividualDto;
import org.kata.dto.ShortIndividualDto;

public interface IndividualService {

    ShortIndividualDto getShortIndividualInformation(String icp);
    IndividualDto getFullIndividualInformation(String icp);
    IndividualAndContactDto getIndividualAndContactInformation(String icp);
    IndividualAndRfPassportDto getIndividualAndRfPassportInformation(String icp);

}
