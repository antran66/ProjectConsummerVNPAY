package com.vnpay.service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.vnpay.common.Common;
import com.vnpay.common.MessageConfig;
import com.vnpay.model.Request;
import com.vnpay.connection.AMQPChannelPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RPCClient {

    private static final Logger logger = LogManager.getLogger(RPCClient.class);

    /**
     * method send message from publisher to consumer and listener response from consumer
     *
     * @param request
     * @param
     * @return response from consumer
     * @throws Exception
     */
    public static String call(Request request) {

        AMQPChannelPool channelPool = AMQPChannelPool.getChannelPoolInstance();
        Channel channel = null;
        try {
            channel = channelPool.getChannel();
            if (channel == null) {
                logger.info("Can not get channel from pool");
                return MessageConfig.FAIL;
            }

            final String corrId = UUID.randomUUID().toString();
            String replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .replyTo(replyQueueName)
                    .build();

            logger.info("Publisher send request to consumer {}", request);
            byte[] dataRequest = Common.getByteArray(request);
            channel.basicPublish("", MessageConfig.QUEUE, props, dataRequest);

            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    response.offer(new String(delivery.getBody(), "UTF-8"));
                }
            }, consumerTag -> {
            });

            String result = response.take();
            channel.basicCancel(ctag);
            return result;
        } catch (Exception ex) {
            logger.error("Fail to received message from queue {}", ex);
            return MessageConfig.FAIL;
        } finally {
            channelPool.returnChannel(channel);
        }
    }

}
