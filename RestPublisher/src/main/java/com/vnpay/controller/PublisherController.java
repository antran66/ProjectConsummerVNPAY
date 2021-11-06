package com.vnpay.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vnpay.common.AppInjector;
import com.vnpay.model.RequestVo;
import com.vnpay.service.PublisherService;
import com.vnpay.service.PublisherServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public class PublisherController {

    private static final Logger logger = LogManager.getLogger(PublisherController.class);
    Injector injector = Guice.createInjector(new AppInjector());
    PublisherService publisherService = injector.getInstance(PublisherServiceImpl.class);

    /**
     * handle and control request from client
     * @param requestVo
     * @return code 200 if run success
     */
    @POST
    @Path("/request")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRequest(RequestVo requestVo) {

        ThreadContext.put("token", requestVo.getTokenKey());
        logger.info("Publisher received request from client: {}", requestVo);
        com.vnpay.model.Response response = publisherService.createRequest(requestVo);
        response.setChecksum(requestVo.getCheckSum());
        response.setData(requestVo.getItem().get(0).getQrInfor());

        logger.info("Publisher send response to client: {}", response);
        ThreadContext.clearAll();

        return Response.ok().entity(response).build();
    }
}
