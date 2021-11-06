package com.vnpay.service;

import com.vnpay.common.Common;
import com.vnpay.common.Converter;
import com.vnpay.common.MessageConfig;
import com.vnpay.common.Validation;
import com.vnpay.connection.JedisPoolUtil;
import com.vnpay.model.Request;
import com.vnpay.model.RequestVo;
import com.vnpay.model.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class PublisherServiceImpl implements PublisherService {

    private static final Logger logger = LogManager.getLogger(PublisherServiceImpl.class);

    /**
     * service to handle request and send request to consumer
     * @param requestVo
     * @return object response to client
     */
    public Response createRequest(RequestVo requestVo) {

        Jedis jedis = null;
        try {
            Request request = Converter.convertData(requestVo);
            String validated = Validation.validate(request);
            if (Strings.isNotEmpty(validated)) {
                logger.info("Request input invalid with {}", validated);
                return Common.setMessageResponse(validated);
            }

            JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
            jedis = jedisPool.getResource();
            if(null == jedis){
                logger.info("Jedis can not get from pool");
                return Common.setMessageResponse(MessageConfig.FAIL);
            }
            if (jedis.exists(request.getTokenKey())) {
                logger.info("Request input invalid with {}", MessageConfig.ERR_TOKEN_KEY);
                return Common.setMessageResponse(MessageConfig.ERR_TOKEN_KEY);
            }

            String message = RPCClient.call(request);

            logger.info("Publisher received response from consumer {}", message);
            return Common.setMessageResponse(message);
        } catch (Exception e) {
            logger.error("Publisher can not send message to Consumer: ", e);
            return Common.setMessageResponse(MessageConfig.FAIL);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
