package com.example.service;

import com.example.common.Converter;
import com.example.common.MessageConfig;
import com.example.common.Validation;
import com.example.model.Request;
import com.example.model.RequestVo;
import com.example.model.Response;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RequestService {

    private static final Logger log = LoggerFactory.getLogger(RequestService.class);

    @Autowired
    private RabbitMQSender mqSender;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Response updateMsgRequest(RequestVo msgRequest) {

        Response response = new Response();
        Request request = Converter.convertData(msgRequest);

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

        log.info("Publisher send request to server {}", request);
        return mqSender.send(request);
    }
}
