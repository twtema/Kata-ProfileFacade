package org.kata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kata.dto.ContactMediumDto;
import org.kata.service.ContactMediumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/contactMedium")
@Tag(name ="Contacts Controller", description = "Contact Media API")
public class ContactMediumController {

    private final ContactMediumService contactMediumService;

    @GetMapping("/getActualNumberPhone")
    @Operation(summary = "Get the actual phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully received"),
            @ApiResponse(responseCode = "400", description = "Not found - The actual number was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })

    public ResponseEntity<String> getActualNumberPhone(@RequestParam String icp) {
        return new ResponseEntity <>(contactMediumService.getActualNumberPhone(icp), HttpStatus.OK);
    }

    @GetMapping("/getActualEmail")
    @Operation(summary = "Get the actual email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully received"),
            @ApiResponse(responseCode = "400", description = "Not found - The actual email was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<String> getActualEmail(@RequestParam String icp) {
        return new ResponseEntity<>(contactMediumService.getActualEmail(icp),HttpStatus.OK);
    }

    @GetMapping("/getAllNumberPhone")
    @Operation(summary = "Get all phone numbers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully received"),
            @ApiResponse(responseCode = "400", description = "Not found - The all number phone was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<List<String>> getAllNumberPhone(String icp) {
        return new ResponseEntity<>(contactMediumService.getAllNumberPhone(icp),HttpStatus.OK);
    }

    @GetMapping("/getAllEmail")
    @Operation(summary = "Get all emails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully received"),
            @ApiResponse(responseCode = "400", description = "Not found - The all number phone was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<List<String>> getAllEmail(String icp) {
        return new ResponseEntity<>(contactMediumService.getAllEmail(icp),HttpStatus.OK);
    }
}
