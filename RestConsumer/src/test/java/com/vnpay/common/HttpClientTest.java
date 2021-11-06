package com.vnpay.common;

import com.vnpay.model.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HttpClientTest {
    private static Request request;

    @BeforeAll
    private static void initialObject() {
        request = Request.builder()
                .tokenKey("1601353776839FT19310RH6P1")
                .build();
    }

    @Test
    void sendPostSuccess() {
        String result = HttpClient.sendPost(MessageConfig.URL_TEST_HTTP_CODE, request);
        Assertions.assertEquals(MessageConfig.SUCCESS, result);
    }

    @Test
    void sendPostFail() {
        String result = HttpClient.sendPost("http://localhost:8090/http/notFound", request);
        Assertions.assertEquals(MessageConfig.FAIL_HTTP, result);
    }

}
