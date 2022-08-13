package com.example.cbrrate.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import com.example.cbrrate.model.CachedCurrencyRates;
import com.example.cbrrate.config.CbrConfig;
import com.example.cbrrate.model.CurrencyRate;
import com.example.cbrrate.parser.CurrencyRateParser;
import com.example.cbrrate.requester.CbrRequester;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyRateService {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final CbrRequester cbrRequester;
    private final CurrencyRateParser currencyRateParser;
    private final CbrConfig cbrConfig;
    private final Cache<LocalDate, CachedCurrencyRates> currencyRateCache;

    public CurrencyRate getCurrencyRate(String currency, LocalDate date) {
        log.info("getCurrencyRate. currency:{}, date:{}", currency, date);
        List<CurrencyRate> rates;

        CachedCurrencyRates cachedCurrencyRates =  currencyRateCache.get(date);
        if (cachedCurrencyRates == null) {
            String urlWithParams = String.format("%s?date_req=%s", cbrConfig.getUrl(), DATE_FORMATTER.format(date));
            String ratesAsXml = cbrRequester.getRatesAsXml(urlWithParams);
            rates = currencyRateParser.parse(ratesAsXml);
            currencyRateCache.put(date, new CachedCurrencyRates(rates));
        } else {
            rates = cachedCurrencyRates.getCurrencyRates();
        }

        return rates.stream().filter(rate -> currency.equals(rate.getCharCode()))
                .findFirst()
                .orElseThrow(() -> new CurrencyRateNotFoundException("Currency Rate not found. Currency:" + currency + ", date:" + date));
    }
}
