package com.example.currencyrateclient.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j

public class HttpClientImpl implements HttpClient {

    @Override
    public Mono<String> performRequest(String url) {
        WebClient webClient = WebClient.create();
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> log.error("Http request error, url:{}", url, error))
                .doOnNext(val -> log.info("val:{}", val));
    }
}
