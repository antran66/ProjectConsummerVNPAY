package com.vnpay.service;

import com.vnpay.common.MessageConfig;
import com.vnpay.model.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RPCClientTest {

    private static Request request;

    @BeforeAll
    private static void initialObject(){
        request = Request.builder()
                .accountNo("0001100014211002")
                .tokenKey("1601353776839FT19310RH6P6")
                .apiID("restPayment")
                .mobile("0145225630")
                .bankCode("970445")
                .payDate("20200929112923")
                .debitAmount(11200)
                .respCode("00")
                .respDesc("SUCCESS")
                .traceTransfer("FT19310RH6P1")
                .checkSum("40e670720b754324af3d3a0ff49b52fb")
                .orderCode("FT19310RH6P1")
                .userName("cntest001")
                .realAmount(11200)
                .build();
    }

    @Test
    void callSuccess(){
        String result = RPCClient.call(request);
        Assertions.assertEquals(MessageConfig.SUCCESS, result);
    }

    @Test
    void callFalse(){
        String result = RPCClient.call(null);
        Assertions.assertEquals(MessageConfig.FAIL, result);
    }
}
