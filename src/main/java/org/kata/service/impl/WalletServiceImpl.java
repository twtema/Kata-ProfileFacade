package org.kata.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.WalletDto;
import org.kata.dto.enums.CurrencyType;
import org.kata.exception.FileReaderException;
import org.kata.exception.WalletNotFoundException;
import org.kata.service.WalletService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final UrlProperties urlProperties;
    private final WebClient loaderWebClient;
    private  String FILENAME = "src/main/resources/CurrencyRate.json";


    public WalletServiceImpl(UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileServiceBaseUrl());
    }

    public List<WalletDto> getWallets(String icp) {
        return loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceGetWallets())
                        .queryParam("icp", icp)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new WalletNotFoundException(
                                "Documents with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<WalletDto>>() {
                })
                .block();
    }

    @Override
    public BigDecimal getTotalBalance(String icp, CurrencyType currencyType) {
        final BigDecimal[] balance = {BigDecimal.ZERO};
        getWallets(icp).forEach(walletDto -> {
            balance[0] = balance[0].add(converterCurrency(walletDto, currencyType));
        });
        return balance[0];
    }

    private BigDecimal converterCurrency(WalletDto walletDto, CurrencyType currencyOriginal) {
        if (walletDto.getCurrencyType().equals(currencyOriginal)) {
            return walletDto.getBalance();
        } else {
            return getBalanceWithRate(walletDto.getBalance(), walletDto.getCurrencyType(), currencyOriginal)
                    .setScale(2, RoundingMode.DOWN);
        }
    }

    private BigDecimal getBalanceWithRate(BigDecimal balance, CurrencyType currencyQuote, CurrencyType currencyOriginal) {
        return  balance.multiply(getRate(currencyQuote))
                .divide(getRate(currencyOriginal), 4);

    }

    private BigDecimal getRate(CurrencyType currencyType)  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map <String, BigDecimal> currancyRate = mapper.readValue(fileReader(), HashMap.class);

    return new BigDecimal(String.valueOf(currancyRate.get(currencyType.name())));

        } catch (JsonProcessingException e) {

            throw new RuntimeException("JsonProcessingException " + e);
        }

    }

    private String fileReader()  {
        try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
            return stream.collect(Collectors.joining("\r\n"));
        } catch (IOException e) {
            throw new FileReaderException("File " + FILENAME + " Reader Exception") ;
        }
    }
}
