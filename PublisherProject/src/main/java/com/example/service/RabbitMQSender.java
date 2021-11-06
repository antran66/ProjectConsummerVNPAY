package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.common.MessageConfig;
import com.example.model.Request;
import com.example.model.Response;

@Service
public class RabbitMQSender {
	private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public Response send(Request request) throws AmqpException {
		log.info("Send request message to Queue with TokenKey {}", request.getTokenKey());
		Response res = (Response) rabbitTemplate.convertSendAndReceive(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY, request);
		log.info("Receive response from queue {}", res.toString());
		return res;
	}

}
