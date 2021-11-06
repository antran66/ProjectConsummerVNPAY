package com.vnpay.connection;

import com.rabbitmq.client.Channel;
import com.vnpay.common.LoadConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AMQPChannelPool {

    private static final Logger logger = LogManager.getLogger(AMQPChannelPool.class);
    private static volatile GenericObjectPool<Channel> internalPool;
    private static AMQPChannelPool instance;
    public static GenericObjectPoolConfig config;

    static {
        PropertiesConfiguration properties = LoadConfiguration.getInstance();
        config = new GenericObjectPoolConfig();
        config.setMaxTotal(properties.getInt("RABBITMQ_MAX_TOTAL"));
        config.setMaxIdle(properties.getInt("RABBITMQ_MAX_IDLE"));
        config.setMinIdle(properties.getInt("RABBITMQ_MIN_IDLE"));
        config.setMaxWaitMillis(properties.getInt("RABBITMQ_MAX_WAIT"));
        config.setBlockWhenExhausted(false);
    }

    public AMQPChannelPool() {
        if(null == internalPool){
            internalPool = new GenericObjectPool<Channel>(new AMQPChannelFactory(), config);
        }
    }

    /**
     * create ChannelPool and return it
     *
     * @return
     */
    public static AMQPChannelPool getChannelPoolInstance() {
        if (null == instance) {
            synchronized (AMQPChannelPool.class) {
                instance = new AMQPChannelPool();
            }
        }
        return instance;
    }

    /**
     * close channelPool
     */
    public static void closeInternalPool() {
        try {
            internalPool.close();
            instance = null;
        } catch (Exception e) {
            logger.error("Could not destroy the pool", e);
        }
    }

    /**
     * create channel from pool
     *
     * @return Channel
     * @throws Exception
     */
    public Channel getChannel() throws Exception {
        return internalPool.borrowObject();
    }

    /**
     * return channel to pool if it's opening
     *
     * @param channel
     */
    public void returnChannel(Channel channel) {
        try {
            if (channel.isOpen()) {
                internalPool.returnObject(channel);
            } else {
                internalPool.invalidateObject(channel);
            }
        } catch (Exception e) {
            logger.error("Could not return the resource to the pool", e);
        }
    }
}
