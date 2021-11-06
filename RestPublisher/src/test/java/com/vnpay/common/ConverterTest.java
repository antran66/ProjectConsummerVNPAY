package com.vnpay.common;

import com.vnpay.model.Item;
import com.vnpay.model.Request;
import com.vnpay.model.RequestVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ConverterTest {

    @Test
    void convertDataAssert(){
        Item item = Item.builder()
                .qrInfor("000201010212262200069084050108VNPAY")
                .build();
        List<Item> items = new ArrayList<>();
        items.add(item);
        RequestVo requestVo = RequestVo.builder()
                .tokenKey("1601353776839FT19310RH6P1")
                .accountNo("test")
                .item(items)
                .build();
        Request request = Converter.convertData(requestVo);

        Assertions.assertEquals(requestVo.getTokenKey(), request.getTokenKey());
    }

}
