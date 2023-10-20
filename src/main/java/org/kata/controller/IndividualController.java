package org.kata.controller;

import lombok.RequiredArgsConstructor;
import org.kata.service.IndividualService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/individual")
public class IndividualController {

    private final IndividualService individualService;


}
