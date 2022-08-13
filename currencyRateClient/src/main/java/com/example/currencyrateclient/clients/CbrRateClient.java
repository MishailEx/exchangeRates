package com.example.currencyrateclient.clients;

import com.example.currencyrateclient.clients.exceptions.HttpClientException;
import com.example.currencyrateclient.clients.exceptions.RateClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.example.currencyrateclient.config.CbrRateClientConfig;
import com.example.currencyrateclient.model.CurrencyRate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service("cbr")
@RequiredArgsConstructor
@Slf4j
public class CbrRateClient implements RateClient {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final CbrRateClientConfig config;
    private final HttpClient httpClient;

    @Override
    public Mono<CurrencyRate> getCurrencyRate(String currency, LocalDate date) {
        log.info("getCurrencyRate currency:{}, date:{}", currency, date);
        String urlWithParams = String.format("%s/%s/%s", config.getUrl(), currency, DATE_FORMATTER.format(date));

        try {
            return httpClient.performRequest(urlWithParams).map(this::parse);
        } catch (HttpClientException ex) {
            throw new RateClientException("Error from Cbr Client host:" + ex.getMessage());
        } catch (Exception ex) {
            log.error("Getting currencyRate error, currency:{}, date:{}", currency, date, ex);
            throw new RateClientException("Can't get currencyRate. currency:" + currency + ", date:" + date);
        }
    }

    private CurrencyRate parse(String rateAsString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(rateAsString, CurrencyRate.class);
    }

//    private CurrencyRate parse(String rateAsString) {
//        try {
//            return objectMapper.readValue(rateAsString, CurrencyRate.class);
//        } catch (Exception ex) {
//            throw new RateClientException("Can't parse string:" + rateAsString);
//        }
//    }
}
