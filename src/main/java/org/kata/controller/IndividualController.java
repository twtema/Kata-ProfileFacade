package org.kata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kata.dto.IndividualAndContactDto;
import org.kata.dto.IndividualAndRfPassportDto;
import org.kata.dto.IndividualDto;
import org.kata.dto.ShortIndividualDto;
import org.kata.service.IndividualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Individual",
        description = "Получение информации о клиенте (Individual) в разных форматах")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/individual")
public class IndividualController {

    private final IndividualService individualService;

    @Operation(summary = "Короткий формат: ФИО и дата рождения")
    @GetMapping("/getShortIndividualInformation")
    public ResponseEntity<ShortIndividualDto> getShort(
            @Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(individualService.getShortIndividualInformation(icp), HttpStatus.OK);
    }

    @Operation(summary = "ФИО, дата рождения и контакты")
    @GetMapping("/getIndividualAndContactInformation")
    public ResponseEntity<IndividualAndContactDto> getIndividualAndContact(
            @Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(individualService.getIndividualAndContactInformation(icp), HttpStatus.OK);
    }

    @Operation(summary = "ФИО, дата рождения и удостоверения личности")
    @GetMapping("/getIndividualAndRfPassportInformation")
    public ResponseEntity<IndividualAndRfPassportDto> getIndividualAndRfPassport(
            @Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(individualService.getIndividualAndRfPassportInformation(icp), HttpStatus.OK);
    }

    @Operation(summary = "Полная информация о клиенте, имеющаяся в объекте Individual")
    @GetMapping("/getFullIndividualInformation")
    public ResponseEntity<IndividualDto> getFull(
            @Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(individualService.getFullIndividualInformation(icp), HttpStatus.OK);
    }
}
