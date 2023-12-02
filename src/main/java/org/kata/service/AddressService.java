package org.kata.service;

import org.kata.dto.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto getActualAddress(String icp);

    List<AddressDto> getAllAddresses(String icp);

}

