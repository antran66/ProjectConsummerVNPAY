package com.vnpay.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vnpay.common.AppInjector;
import com.vnpay.connection.JedisPoolUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ConsumerServiceImplTest {

    Injector injector = Guice.createInjector(new AppInjector());
    ConsumerService consumerService = injector.getInstance(ConsumerServiceImpl.class);

    @Test
    void receiveAndUpdateRequestTrue() {
        consumerService.receiveAndUpdateRequest();
        JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
        Jedis jedis = jedisPool.getResource();
        boolean isExist = jedis.exists("1601353776839FT19310RH6P3");
        jedis.close();
        jedisPool.close();

        Assertions.assertEquals(true, isExist);
    }

}
