package org.kata.controller;

import lombok.RequiredArgsConstructor;
import org.kata.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/address")
public class AddressController {
    private final AddressService addressService;

}
