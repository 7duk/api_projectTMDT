package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class Currency {
    @Value("${exchangerate.api.key}")
    private String apiKey;

    private final String apiUrl = "https://v6.exchangerate-api.com/v6/";

    private final RestTemplate restTemplate = new RestTemplate();



    public Double convertVndToUsd(Double amountInVnd) {
        String url = apiUrl + apiKey+"/latest/VND";
        // Gửi yêu cầu đến ExchangeRate-API để lấy tỷ giá
        ExchangeRatesResponse response = restTemplate.getForObject(url, ExchangeRatesResponse.class);

        // Xử lý kết quả và chuyển đổi số tiền từ VND sang USD
        if (response != null && response.getConversion_rates() != null) {
            Double exchangeRate = response.getConversion_rates().get("USD");
            return amountInVnd * exchangeRate;
        }

        return null;
    }
}
