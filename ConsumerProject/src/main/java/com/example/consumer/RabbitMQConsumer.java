package com.example.consumer;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.common.MessageConfig;
import com.example.model.Request;
import com.example.model.Response;
import com.example.service.ResponseService;


@Component
public class RabbitMQConsumer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Autowired
    private ResponseService responseService;

    @RabbitListener(queues = MessageConfig.QUEUE)
    public Response receivedMessage(Request request) {
        ThreadContext.put("request.token", request.getTokenKey());
        log.info("Server received message from queue: {}", request);
        Response response = responseService.updateRequest(request);
        log.info("Server response message to publisher: {}", response.toString());
        ThreadContext.clearAll();
        return response;
    }
}
