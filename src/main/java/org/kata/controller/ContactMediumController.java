package org.kata.controller;

import lombok.RequiredArgsConstructor;
import org.kata.service.ContactMediumService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/contactMedium")
public class ContactMediumController {

    private final ContactMediumService contactMediumService;


}
