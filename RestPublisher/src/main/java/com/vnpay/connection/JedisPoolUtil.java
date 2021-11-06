package com.vnpay.connection;

import com.vnpay.common.LoadConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
    private static final Logger logger = LogManager.getLogger(JedisPoolUtil.class);
    private static volatile JedisPool jedisPool = null;

    private JedisPoolUtil() {
    }

    /**
     * create JedisPool and return instance
     *
     * @return instance of JedisPool
     */
    public static JedisPool getJedisPoolInstance() {
        try {
            PropertiesConfiguration properties = LoadConfiguration.getInstance();
            if (null == jedisPool) {
                synchronized (JedisPoolUtil.class) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(properties.getInt("REDIS_MAX_TOTAL"));
                    poolConfig.setMaxIdle(properties.getInt("REDIS_MAX_IDLE"));
                    poolConfig.setMaxWaitMillis(properties.getInt("REDIS_MAX_WAIT"));
                    poolConfig.setTestOnBorrow(true);

                    jedisPool = new JedisPool(poolConfig, properties.getString("REDIS_HOST_MASTER"), properties.getInt("REDIS_PORT"), properties.getInt("REDIS_TIME_OUT"), properties.getString("REDIS_PASSWORD"));
                }
            }
        } catch (Exception e) {
            logger.error("Fail to load file properties: ", e);
        }
        return jedisPool;
    }

    /**
     * close channelPool
     */
    public static void closeJedisPool() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }

}
