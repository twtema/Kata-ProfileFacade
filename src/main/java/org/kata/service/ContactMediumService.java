package org.kata.service;

import org.kata.dto.ContactMediumDto;
import org.kata.dto.enums.ContactMediumType;

import java.util.List;

public interface ContactMediumService {
    List<String> getAllEmail(String icp);

    List<String> getAllNumberPhone(String icp);

    String getActualEmail(String icp);

    String getActualNumberPhone(String icp);
}
