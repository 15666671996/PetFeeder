package com.group2.pet_feeder.other;

import com.group2.pet_feeder.entity.Message;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class HttpClient {
    private final RestTemplate template;

    public HttpClient(RestTemplateBuilder restTemplateBuilder) {
        this.template = restTemplateBuilder.build();
    }

//    private static final String host = "http://pet-feeder-cv-service";
    private static final String host = "http://127.0.0.1";
    private static final String port = ":5000";

    public byte[] getPhoto() {
        String url = host + port + "/take-photo-req";
        return template.getForObject(url, byte[].class);
    }

    public Message checkEmpty() {
        String url = host + port +"/is-empty-req";
        return template.getForObject(url, Message.class);
    }

    public Message enablePump() {
        String url = host + port +"/enable-pump";
        return template.getForObject(url, Message.class);
    }

    public Message disablePump() {
        String url = host + port +"/disable-pump";
        return template.getForObject(url, Message.class);
    }

    public Message serveFood(String userId) {
        String url = host + port +"/serve-food";
        Message message = checkEmpty();
        if ("true".equals(message.getMessage())||true) {
            return template.getForObject(url, Message.class);
        } else {
            message.setMessage("food is out!");
            return message;
        }
    }


}
