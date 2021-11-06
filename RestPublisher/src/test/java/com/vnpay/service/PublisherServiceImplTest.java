package com.vnpay.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vnpay.common.AppInjector;
import com.vnpay.model.Item;
import com.vnpay.model.RequestVo;
import com.vnpay.model.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PublisherServiceImplTest {

    Injector injector = Guice.createInjector(new AppInjector());
    PublisherService publisherService = injector.getInstance(PublisherServiceImpl.class);
    private static RequestVo requestVo;

    @BeforeAll
    private static void initialObject(){
        Item item = Item.builder()
                .qrInfor("000201010212262200069084050108VNPAY0015204573253037045405112005802VN5904VBAN62660307Vban001051801200929114391692707082QXUI4J40817Thanh")
                .quantity(1)
                .build();
        List<Item> items = new ArrayList<>();
        items.add(item);
        requestVo = RequestVo.builder()
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
                .item(items)
                .checkSum("40e670720b754324af3d3a0ff49b52fb")
                .orderCode("FT19310RH6P1")
                .userName("cntest001")
                .realAmount(11200)
                .build();
    }

    @Test
    void createRequestTrue(){
        Response response = publisherService.createRequest(requestVo);
        Assertions.assertEquals("00", response.getCode());
    }

    @Test
    void createRequestFalse(){
        Response response = publisherService.createRequest(requestVo);
        Assertions.assertEquals("01", response.getCode());
    }
}
