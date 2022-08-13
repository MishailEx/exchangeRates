package com.example.currencyrateclient.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.example.currencyrateclient.clients.RateClient;

import com.example.currencyrateclient.model.CurrencyRate;
import com.example.currencyrateclient.model.RateType;


import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
public class CurrencyRateEndpointService {

    private final Map<String, RateClient> clients;

    public CurrencyRateEndpointService(Map<String, RateClient> clients) {
        this.clients = clients;
    }

    public Mono<CurrencyRate> getCurrencyRate(RateType rateType, String currency, LocalDate date) {
        log.info("getCurrencyRate. rateType:{}, currency:{}, date:{}", rateType, currency, date);
        RateClient client = clients.get(rateType.getServiceName());
        return client.getCurrencyRate(currency, date);
    }
}
