package com.vnpay.common;

import com.vnpay.model.Request;
import com.vnpay.model.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CommonTest {

    @Test
    void setMessageResponseTrue() {
        Response response = Common.setMessageResponse(MessageConfig.SUCCESS);
        Assertions.assertEquals(MessageConfig.CODE_SUCCESS, response.getCode());
    }

    @Test
    void setMessageResponseFalse() {
        Response response = Common.setMessageResponse(MessageConfig.ERR_TOKEN_KEY);
        Assertions.assertEquals(MessageConfig.CODE_FAIL, response.getCode());
    }

    @Test
    void checkDateFormatAssertTrue() {
        boolean isDate = Common.checkDateFormat("20200929112923");
        Assertions.assertEquals(true, isDate);
    }

    @Test
    void checkDateFormatAssertFalse() {
        boolean isDate = Common.checkDateFormat("20200929112HTK");
        Assertions.assertEquals(false, isDate);
    }

    @Test
    void getByteArrayAssert() {
        try {
            Request request = Request.builder()
                    .tokenKey("1601353776839FT19310RH6P1")
                    .accountNo("test")
                    .build();

            byte[] valueRequest = Common.getByteArray(request);

            Assertions.assertNotEquals(request, valueRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
