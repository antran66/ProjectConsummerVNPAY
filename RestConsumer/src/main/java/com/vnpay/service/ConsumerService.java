package com.vnpay.service;

/**
 * Interface of service Consumer
 */
public interface ConsumerService {
    /**
     * receive message from queue and save to database
     * @param
     */
    void receiveAndUpdateRequest();
}
