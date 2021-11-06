package com.vnpay;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vnpay.common.AppInjector;
import com.vnpay.connection.AMQPChannelPool;
import com.vnpay.connection.DBConnection;
import com.vnpay.connection.JedisPoolUtil;
import com.vnpay.service.ConsumerService;
import com.vnpay.service.ConsumerServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainApplication {
    private static final Logger logger = LogManager.getLogger(MainApplication.class);

    /**
     * main application
     * @param args
     */
    public static void main(String[] args) {
        try{
            Injector injector = Guice.createInjector(new AppInjector());
            ConsumerService consumerService = injector.getInstance(ConsumerServiceImpl.class);
            logger.info("Consumer start running");
            consumerService.receiveAndUpdateRequest();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    logger.info("Shutting down the application...");
                    JedisPoolUtil.closeJedisPool();
                    AMQPChannelPool.closeInternalPool();
                    DBConnection.closeConnection();
                    logger.info("Done, exit.");
                } catch (Exception e) {
                    logger.error("Fail to shutting down server", e);
                }
            }));

            logger.info("Application started.%nStop the application using CTRL+C");
            Thread.currentThread().join();
        }catch (InterruptedException ex){
            logger.error("Fail to start server: ", ex);
        }
    }
}
