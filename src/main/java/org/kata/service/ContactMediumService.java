package org.kata.service;

import java.util.List;

public interface ContactMediumService {
    List<String> getAllEmail(String icp);

    List<String> getAllNumberPhone(String icp);

    String getActualPersonalEmail(String icp);

    String getActualPersonalNumberPhone(String icp);

    String getActualBusinessEmail(String icp);

    String getActualBusinesslNumberPhone(String icp);
}
