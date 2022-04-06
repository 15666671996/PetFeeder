package com.group2.pet_feeder.other;

import com.group2.pet_feeder.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class HttpClient {
    private final RestTemplate template;

    public HttpClient(RestTemplateBuilder restTemplateBuilder) {
        this.template = restTemplateBuilder.build();
    }

    @Value("${cv-server.endpoint}")
    private static final String endpoint = "127.0.0.1:5000";

    public byte[] getPhoto() {
        String url = "http://" + endpoint + "/take-photo-req";
        return template.getForObject(url, byte[].class);
    }

    public Message checkEmpty() {
        String url = "http://" + endpoint + "/is-empty-req";
        return template.getForObject(url, Message.class);
    }

    public Message enablePump() {
        String url = "http://" + endpoint + "/enable-pump";
        return template.getForObject(url, Message.class);
    }

    public Message disablePump() {
        String url = "http://" + endpoint + "/disable-pump";
        return template.getForObject(url, Message.class);
    }

    public Message serveFood(String userId) {
        String url = "http://" + endpoint + "/serve-food";
        Message message = checkEmpty();
        if ("true".equals(message.getMessage()) || true) {
            return template.getForObject(url, Message.class);
        } else {
            message.setMessage("food is out!");
            return message;
        }
    }


}
