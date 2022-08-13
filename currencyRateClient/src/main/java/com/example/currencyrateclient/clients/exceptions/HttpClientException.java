package com.example.currencyrateclient.clients.exceptions;

public class HttpClientException extends RuntimeException {
    public HttpClientException(String msg) {
        super(msg);
    }
}
