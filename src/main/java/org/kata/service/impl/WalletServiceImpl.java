package org.kata.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.WalletDto;
import org.kata.dto.enums.CurrencyType;
import org.kata.exception.FileReaderException;
import org.kata.exception.JsonReaderException;
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
    private final String FILENAME = "src/main/resources/CurrencyRate.json";


    public WalletServiceImpl(UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileServiceBaseUrl());
    }

    /**
     * Метод для получения списка кошельков пользователя по его icp.
     *
     * @param icp идентификатор пользователя
     * @return список кошельков пользователя
     * @throws WalletNotFoundException если кошельки с указанным icp не найдены
     */
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

    /**
     * Метод для получения общего баланса пользователя в указанной валюте.
     *
     * @param icp          идентификатор пользователя
     * @param currencyType валюта, в которой необходимо получить общий баланс
     * @return общий баланс пользователя в указанной валюте
     */
    @Override
    public BigDecimal getTotalBalance(String icp, CurrencyType currencyType) {
        final BigDecimal[] balance = {BigDecimal.ZERO};
        getWallets(icp).forEach(walletDto ->
            balance[0] = balance[0].add(converterCurrency(walletDto, currencyType)));
        return balance[0];
    }

    /**
     * Метод для конвертации баланса кошелька из одной валюты в другую.
     *
     * @param walletDto        объект кошелька, который необходимо конвертировать
     * @param currencyOriginal валюта, в которой указан оригинальный баланс кошелька
     * @return сконвертированный баланс кошелька в указанную валюту
     */
    private BigDecimal converterCurrency(WalletDto walletDto, CurrencyType currencyOriginal) {
        if (walletDto.getCurrencyType().equals(currencyOriginal)) {
            return walletDto.getBalance();
        } else {
            return getBalanceWithRate(walletDto.getBalance(), walletDto.getCurrencyType(), currencyOriginal)
                    .setScale(2, RoundingMode.DOWN);
        }
    }

    /**
     * Метод для получения баланса кошелька с учетом курса валют.
     *
     * @param balance          оригинальный баланс кошелька
     * @param currencyQuote    валюта, в которой указан баланс кошелька
     * @param currencyOriginal валюта, в которую необходимо сконвертировать баланс кошелька
     * @return баланс кошелька в указанной валюте с учетом курса валют
     */
    private BigDecimal getBalanceWithRate(BigDecimal balance, CurrencyType currencyQuote, CurrencyType currencyOriginal) {
        return balance.multiply(getRate(currencyQuote))
                .divide(getRate(currencyOriginal), RoundingMode.HALF_UP);

    }

    /**
     * Метод для получения курса валюты из файла.
     *
     * @param currencyType валюта, курс которой необходимо получить
     * @return курс указанной валюты
     * @throws RuntimeException если произошла ошибка при обработке JSON-файла с курсами валют
     */
    private BigDecimal getRate(CurrencyType currencyType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            HashMap<String, BigDecimal> currancyRate = mapper.readValue(fileReader(), HashMap.class);

            return new BigDecimal(String.valueOf(currancyRate.get(currencyType.name())));

        } catch (JsonProcessingException e) {

            throw new JsonReaderException("Json Reader Exception " + e);
        }

    }

    /**
     * Метод для чтения файла с курсами валют.
     *
     * @return содержимое файла с курсами валют
     * @throws FileReaderException если произошла ошибка при чтении файла с курсами валют
     */
    private String fileReader() {
        try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
            return stream.collect(Collectors.joining("\r\n"));
        } catch (IOException e) {
            throw new FileReaderException("File " + FILENAME + " Reader Exception");
        }
    }
}
