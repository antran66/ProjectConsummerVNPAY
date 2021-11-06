package com.example.service;

import com.example.common.HttpClient;
import com.example.common.MessageConfig;
import com.example.common.Validation;
import com.example.consumer.RabbitMQConsumer;
import com.example.model.Request;
import com.example.model.Response;
import com.example.repository.RequestRepository;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
public class ResponseService {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Response updateRequest(Request request){

        Response response = new Response();
        try {
            String validated = Validation.validate(request);
            if (Strings.isNotEmpty(validated)) {
                response.setCode(MessageConfig.CODE_FAIL);
                response.setMessage(validated);
                response.setChecksum(request.getCheckSum());
                return response;
            }
            Request checkToken = (Request) redisTemplate.opsForValue().get(request.getTokenKey());
            if(checkToken != null){
                response.setCode(MessageConfig.CODE_FAIL);
                response.setMessage(MessageConfig.ERR_TOKEN_KEY);
                response.setChecksum(request.getCheckSum());
                return response;
            }
            redisTemplate.opsForValue().set(request.getTokenKey(), request);
            redisTemplate.expire(request.getTokenKey(), 1, TimeUnit.DAYS);

            Request requestUpdate = requestRepository.save(request);
            if (null == requestUpdate) {
                response.setCode(MessageConfig.CODE_FAIL);
                response.setMessage(MessageConfig.ERR_SAVE_DB);
                response.setChecksum(request.getCheckSum());
                return response;
            }
            log.info("Update {} to database", request);

            log.info("Send request to {} with token {} ", MessageConfig.URL, request.getTokenKey());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            ResponseEntity<String> responseURL = HttpClient.sendPost(MessageConfig.URL, headers, request);

            if (responseURL.getStatusCode() == HttpStatus.OK) {
                response.setCode(MessageConfig.CODE_SUCCESS);
                response.setMessage(MessageConfig.SUCCESS);
                response.setChecksum(request.getCheckSum());
                response.setResponseId(String.valueOf(request.getId()));
                return response;
            }
        }catch (IllegalArgumentException illegalException){
            response.setCode(MessageConfig.CODE_FAIL);
            response.setMessage(MessageConfig.ERR_SAVE_DB.concat(illegalException.getMessage()));
            log.error(MessageConfig.ERR_SAVE_DB, illegalException);
        }catch (RestClientException httpClientException){
            response.setCode(MessageConfig.CODE_FAIL);
            response.setMessage(MessageConfig.ERR_HTTP_REQUEST.concat(httpClientException.getMessage()));
            log.info(MessageConfig.ERR_HTTP_REQUEST, httpClientException);
        }catch (Exception ex){
            response.setCode(MessageConfig.CODE_FAIL);
            response.setMessage(ex.getMessage());
            log.error("Exception: ", ex);
        }finally {
            return response;
        }
    }
}