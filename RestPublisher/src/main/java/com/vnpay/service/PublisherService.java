package com.vnpay.service;

import com.vnpay.model.RequestVo;
import com.vnpay.model.Response;

/**
 * Interface for service publisher
 */
public interface PublisherService {
    /**
     * service to handle request and send request to consumer
     * @param requestVo
     * @return object response to client
     */
    Response createRequest(RequestVo requestVo);
}
