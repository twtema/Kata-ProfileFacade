package org.kata.service;

import org.kata.dto.WalletDto;
import org.kata.dto.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    List<WalletDto> getWallets(String icp);

    BigDecimal getTotalBalance(String icp, CurrencyType currencyType);
}
