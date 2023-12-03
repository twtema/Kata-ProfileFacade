package org.kata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kata.dto.WalletDto;
import org.kata.dto.enums.CurrencyType;
import org.kata.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Wallet", description = "This class is designed to work with Individual wallets")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/wallets")
public class WalletController {
    private final WalletService walletService;

    @Operation(summary = "Получить Wallet по icp",
            description = "Возвращает DTO Wallet по ICP")
    @GetMapping
    public ResponseEntity<List<WalletDto>> getWallet(@Parameter(description = "ICP identifier",
            required = true) @RequestParam String icp) {
        return new ResponseEntity<>(walletService.getWallets(icp), HttpStatus.OK);
    }

    @Operation(summary = "Получить сумму всех кошельков заданной валюте(по умолчанию рубли)",
            description = "Возвращает String Total balance")
    @GetMapping("/totalbalance")
    public ResponseEntity <BigDecimal> getTotalBalance
            (@Parameter(description = "ICP identifier", required = true) @RequestParam String icp,
             @Parameter(description = "CurrencyType", required = true)
             @RequestParam (name = "currencyType", defaultValue = "RUB") CurrencyType currencyType) {
        return new ResponseEntity<>(walletService.getTotalBalance(icp, currencyType), HttpStatus.OK);
    }



}
