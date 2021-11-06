package com.vnpay.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vnpay.MainApplication;
import com.vnpay.model.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HttpClient {

    private static final Logger logger = LogManager.getLogger(HttpClient.class);

    /**
     * create httpClient and send message
     * @param url
     * @param request
     * @return response of httpClient
     */
    public static String sendPost(String url, Request request) {

        CloseableHttpClient client = null;
        try {
            PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
            pool.setDefaultMaxPerRoute(1);
            pool.setMaxTotal(20);

            client = HttpClients.custom().setConnectionManager(pool).build();
            HttpPost httpPost = new HttpPost(url);
            request.setItem("");
            ObjectMapper mapper = new ObjectMapper();
            StringEntity entity = new StringEntity(mapper.writeValueAsString(request));
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            logger.info("Send post request to {} with data {}", url, request);
            CloseableHttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            return Common.setMessageFromCode(statusCode);
        } catch (IOException e) {
            logger.error("Fail to send http request:", e);
            return MessageConfig.FAIL_HTTP;
        }finally {
            try {
                if (client != null){
                    client.close();
                }
            } catch (IOException e) {
                logger.error("Fail to close connection of http client: ",e);
            }
        }
    }
}
