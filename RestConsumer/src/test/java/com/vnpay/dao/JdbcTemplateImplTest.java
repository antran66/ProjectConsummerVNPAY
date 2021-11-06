package com.vnpay.dao;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vnpay.common.AppInjector;
import com.vnpay.model.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JdbcTemplateImplTest {

    Injector injector = Guice.createInjector(new AppInjector());
    JdbcTemplate jdbctemplate = injector.getInstance(JdbcTemplateImpl.class);
    private static Request request;

    @BeforeAll
    private static void initialObject(){
        request = Request.builder()
                .accountNo("0001100014211002")
                .tokenKey("1601353776839FT19310RH6P2")
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
    void insertRequestSuccess(){
        boolean result = jdbctemplate.insertRequest(request);
        Assertions.assertEquals(true, result);
    }

    @Test
    void insertRequestFalse(){
        boolean result = jdbctemplate.insertRequest(null);
        Assertions.assertEquals(false, result);
    }
}
