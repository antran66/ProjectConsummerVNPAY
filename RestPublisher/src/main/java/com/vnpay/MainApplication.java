package com.vnpay;

import com.vnpay.common.MessageConfig;
import com.vnpay.connection.AMQPChannelPool;
import com.vnpay.connection.JedisPoolUtil;
import com.vnpay.controller.PublisherController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class MainApplication {

    private static final Logger logger = LogManager.getLogger(MainApplication.class);

    /**
     * create server jetty to run application
     *
     * @return Server
     */
    public static Server startServer() {
        final ResourceConfig config = new ResourceConfig(PublisherController.class);
        return JettyHttpContainerFactory.createServer(URI.create(MessageConfig.BASE_URI), config);
    }

    /**
     * main application
     *
     * @param args
     */
    public static void main(String[] args) {
        try {

            final Server server = startServer();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    logger.info("Shutting down the application...");
                    server.stop();
                    AMQPChannelPool.closeInternalPool();
                    JedisPoolUtil.closeJedisPool();
                    logger.info("Done, exit.");
                } catch (Exception e) {
                    logger.error("Fail to shutting down server", e);
                }
            }));

            logger.info("Application started.%nStop the application using CTRL+C");
            Thread.currentThread().join();

        } catch (InterruptedException ex) {
            logger.error("Fail to start server: ", ex);
        }

    }
}