package com.vnpay.connection;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.vnpay.common.LoadConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AMQPChannelFactory implements PooledObjectFactory<Channel> {

    private static final Logger logger = LogManager.getLogger(AMQPChannelFactory.class);
    private static volatile ConnectionFactory factory = null;
    private Connection connection;

    /**
     * initial configuration's ConnectionFactory and create connection
     */
    public AMQPChannelFactory() {
        try {
            if (null == factory) {
                PropertiesConfiguration properties = LoadConfiguration.getInstance();
                factory = new ConnectionFactory();
                factory.setUsername(properties.getString("RABBITMQ_USERNAME"));
                factory.setPassword(properties.getString("RABBITMQ_PASSWORD"));
                factory.setVirtualHost(properties.getString("RABBITMQ_VIRTUALHOST"));
                factory.setHost(properties.getString("RABBITMQ_HOST"));
                factory.setPort(properties.getInt("RABBITMQ_PORT"));
                factory.setRequestedHeartbeat(properties.getInt("RABBITMQ_HEARTBEAT"));
            }
            connection = factory.newConnection();
        } catch (Exception e) {
            logger.error("Can not connection to RabbitMQ: ", e);
        }
    }

    /**
     * create PoolObject Channel
     *
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<Channel> makeObject() throws Exception {
        return new DefaultPooledObject<Channel>(connection.createChannel());
    }

    /**
     * close channel if it's opening
     *
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<Channel> pooledObject) throws Exception {
        final Channel channel = pooledObject.getObject();
        if (channel.isOpen()) {
            try {
                channel.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * check object open
     *
     * @param pooledObject
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<Channel> pooledObject) {
        final Channel channel = pooledObject.getObject();
        return channel.isOpen();
    }

    @Override
    public void activateObject(PooledObject<Channel> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<Channel> p) throws Exception {

    }
}
