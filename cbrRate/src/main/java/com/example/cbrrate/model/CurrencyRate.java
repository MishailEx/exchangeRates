package com.example.cbrrate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CurrencyRate {
    String numCode;
    String charCode;
    String nominal;
    String name;
    String value;
}
