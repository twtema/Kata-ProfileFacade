package org.kata.service;

import org.kata.dto.ContactMediumDto;
import org.kata.dto.enums.ContactMediumType;

import java.util.List;

public interface ContactMediumService {
    List<String> getAllEmail(String icp, String conversationId);

    List<String> getAllNumberPhone(String icp, String conversationId);

    String getActualEmail(String icp, String conversationId);

    String getActualNumberPhone(String icp, String conversationId);
}
