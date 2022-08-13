package com.example.cbrrate.parser;


import org.junit.jupiter.api.Test;
import com.example.cbrrate.model.CurrencyRate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CurrencyRateParserXmlTest {

    @Test
    void parseTest() throws IOException, URISyntaxException {
        CurrencyRateParserXml parser = new CurrencyRateParserXml();
        URI uri = ClassLoader.getSystemResource("cbr_response.xml").toURI();
        String ratesXml = Files.readString(Paths.get(uri), Charset.forName("Windows-1251"));

        List<CurrencyRate> rates = parser.parse(ratesXml);

        assertThat(rates.size()).isEqualTo(34);
        assertThat(rates.contains(getUsdRate())).isTrue();
        assertThat(rates.contains(getEurRate())).isTrue();
        assertThat(rates.contains(getJpyRate())).isTrue();
    }

    CurrencyRate getUsdRate() {
        return CurrencyRate.builder()
                .numCode("840")
                .charCode("USD")
                .nominal("1")
                .name("Доллар США")
                .value("74,0448")
                .build();
    }

    CurrencyRate getEurRate() {
        return CurrencyRate.builder()
                .numCode("978")
                .charCode("EUR")
                .nominal("1")
                .name("Евро")
                .value("89,4461")
                .build();
    }

    CurrencyRate getJpyRate() {
        return CurrencyRate.builder()
                .numCode("392")
                .charCode("JPY")
                .nominal("100")
                .name("Японских иен")
                .value("69,4702")
                .build();
    }
}