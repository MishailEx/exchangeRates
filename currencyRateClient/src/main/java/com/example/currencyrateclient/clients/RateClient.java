package com.example.currencyrateclient.clients;

import reactor.core.publisher.Mono;
import com.example.currencyrateclient.model.CurrencyRate;

import java.time.LocalDate;

public interface RateClient {

    Mono<CurrencyRate> getCurrencyRate(String currency, LocalDate date);
}
