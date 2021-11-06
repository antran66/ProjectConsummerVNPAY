package com.example.common;

import com.example.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class HttpClient {

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    public static ResponseEntity<String> sendPost(String url, HttpHeaders headers, Request request)
			throws RestClientException {
        ResponseEntity<String> response;

        RestTemplate template = new RestTemplate();

        request.setItem(null);
        HttpEntity<Request> entity = new HttpEntity<>(request, headers);
        response = template.postForEntity(url, entity, String.class);
        log.info("Response from {} status code {} ", url, response.getStatusCode());

        return response;
    }

}
