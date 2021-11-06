package com.vnpay.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.vnpay.common.AppInjector;
import com.vnpay.common.Common;
import com.vnpay.common.HttpClient;
import com.vnpay.common.MessageConfig;
import com.vnpay.connection.JedisPoolUtil;
import com.vnpay.dao.JdbcTemplate;
import com.vnpay.dao.JdbcTemplateImpl;
import com.vnpay.model.Request;
import com.vnpay.connection.AMQPChannelPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ConsumerServiceImpl implements ConsumerService {

    private static final Logger logger = LogManager.getLogger(ConsumerServiceImpl.class);

    /**
     * receive message from queue and save to database
     * @param
     */
    public void receiveAndUpdateRequest() {

        try {
            AMQPChannelPool channelPool = AMQPChannelPool.getChannelPoolInstance();
            Channel channel = channelPool.getChannel();
            if(channel == null){
                throw new Exception("Can not get channel from pool");
            }
            logger.info("Connection RabbitMQ success");
            channel.queueDeclare(MessageConfig.QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";
                JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
                Jedis jedis = null;
                try {
                    byte[] byteArray = delivery.getBody();
                    Request request = (Request) Common.deserialize(byteArray);
                    ThreadContext.put("token", request.getTokenKey());
                    logger.info("Consumer received request: {}", request);

                    jedis = jedisPool.getResource();
                    if (null == jedis) {
                        logger.info("Can not get Jedis from pool");
                        response = MessageConfig.FAIL_TO_GET_REDIS;
                        return;
                    }
                    logger.info("Connection jedis success");
                    if (jedis.exists(request.getTokenKey())) {
                        logger.info("Redis exist key: ", request.getTokenKey());
                        response = MessageConfig.ERR_TOKEN_KEY;
                        return;
                    }

                    Injector injector = Guice.createInjector(new AppInjector());
                    JdbcTemplate jdbctemplate = injector.getInstance(JdbcTemplateImpl.class);
                    boolean isSuccess = jdbctemplate.insertRequest(request);
                    if (!isSuccess) {
                        logger.info("Can not insert to database");
                        response = MessageConfig.FAIL_TO_SAVE;
                        return;
                    }
                    jedis.set(request.getTokenKey(), String.valueOf(request));
                    jedis.expire(request.getTokenKey(), MessageConfig.SECOND_TO_LIVE);
                    logger.info("Caching request with {} seconds", MessageConfig.SECOND_TO_LIVE);
                    response = HttpClient.sendPost(MessageConfig.URL_TEST_HTTP_CODE, request);
                    logger.info("Received message from http client {} with message {}", MessageConfig.URL_TEST_HTTP_CODE, response);

                } catch (ClassNotFoundException | RuntimeException e) {
                    logger.error("Can not send http request: ", e);
                    response = MessageConfig.FAIL_HTTP;
                } finally {
                    logger.info("Consumer send response to Publisher: {}", response);
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    jedis.close();
                    ThreadContext.clearAll();
                    channelPool.returnChannel(channel);
                }
            };
            channel.basicConsume(MessageConfig.QUEUE, false, deliverCallback, (consumerTag -> {}));

        } catch (Exception e) {
            logger.error("Fail to init channel pool: ", e);
        }
    }

}
