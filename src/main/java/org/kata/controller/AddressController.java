package org.kata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kata.dto.AddressDto;
import org.kata.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/address")
@Tag(name = "Addresses Controller", description = "Address API")
public class AddressController {

    private final AddressService addressService;

    @GetMapping(value = "/getActualAddress",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get the actual address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully received"),
            @ApiResponse(responseCode = "400", description = "Not found - The actual address was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<AddressDto> getActualAddress(
            @RequestHeader(value = "conversationId", required = false) String conversationId,
            @RequestParam String icp
    ) {
        return new ResponseEntity<>(addressService.getActualAddress(icp, conversationId), HttpStatus.OK);
    }

    @GetMapping("/getAllAddresses")
    @Operation(summary = "Get the all addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully received"),
            @ApiResponse(responseCode = "400", description = "Not found - The all addresses was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<List<AddressDto>> getAllAddresses(
            @RequestHeader(value = "conversationId", required = false) String conversationId,
            @RequestParam String icp
    ) {
        return new ResponseEntity<>(addressService.getAllAddresses(icp, conversationId), HttpStatus.OK);
    }
}

