package com.group2.pet_feeder.other;

import com.group2.pet_feeder.entity.Message;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class HttpClient {
    private final RestTemplate template;

    public HttpClient(RestTemplateBuilder restTemplateBuilder) {
        this.template = restTemplateBuilder.build();
    }

    private String endpoint = System.getenv("CV_ENDPOINT");

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
        if ("true".equals(message.getMessage())) {
            return template.getForObject(url, Message.class);
        } else {
            message.setMessage("food is out!");
            return message;
        }
    }

    public Message getWaterStatus() {
        String url = "http://" + endpoint + "/water-status";
        return template.getForObject(url, Message.class);
    }

    public Message getWeightStatus() {
        String url = "http://" + endpoint + "/weight-status";
        return template.getForObject(url, Message.class);
    }

}
