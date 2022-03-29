package com.group2.pet_feeder.other;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class HttpClient {
    private final RestTemplate template;

    public HttpClient(RestTemplateBuilder restTemplateBuilder) {
        this.template = restTemplateBuilder.build();
    }
    public byte[] getPhoto(){
        String url = "http://127.0.0.1:5000/take-photo-req";
        return template.getForObject(url, byte[].class);
    }

}
