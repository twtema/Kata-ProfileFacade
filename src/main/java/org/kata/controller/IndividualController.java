package org.kata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kata.dto.IndividualAndContactDto;
import org.kata.dto.IndividualAndRfPassportDto;
import org.kata.dto.ShortIndividualDto;
import org.kata.service.IndividualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Individual",
        description = "This tool is a beautiful facade to get information about individual person in convenient formats")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/individual")
public class IndividualController {

    private final IndividualService individualService;

    @Operation(summary = "Small test1")
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello, bro", HttpStatus.OK);
    }

    @Operation(summary = "Small test2")
    @GetMapping("/getfeignhello")
    public ResponseEntity<String> getFeignHello() {
        return new ResponseEntity<>(individualService.getFeignHello(), HttpStatus.OK);
    }

//    @Operation(summary = "Get minimum Information from entity Individual")
//    @GetMapping("/getShortIndividualInformation")
//    public ResponseEntity<ShortIndividualDto> getShort(
//            @Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
//        return new ResponseEntity<>(individualService.getShortIndividualInformation(icp), HttpStatus.OK);
//    }
//
//    @Operation(summary = "Get ShortIndividualInformation and Contacts from entity Individual")
//    @GetMapping("/getIndividualAndContactInformation")
//    public ResponseEntity<IndividualAndContactDto> getIndividualAndContact(
//            @Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
//        return new ResponseEntity<>(individualService.getIndividualAndContactInformation(icp), HttpStatus.OK);
//    }
//
//    @Operation(summary = "Get ShortIndividualInformation and RF Passports from entity Individual")
//    @GetMapping("/getIndividualAndRfPassportInformation")
//    public ResponseEntity<IndividualAndRfPassportDto> getIndividualAndRfPassport(
//            @Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
//        return new ResponseEntity<>(individualService.getIndividualAndRfPassportInformation(icp), HttpStatus.OK);
//    }

}
