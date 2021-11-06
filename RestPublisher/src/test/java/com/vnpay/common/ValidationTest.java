package com.vnpay.common;

import com.vnpay.model.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidationTest {

    @Test
    void validateAssertTrue(){
        Request request = Request.builder()
                .tokenKey("1601353776839FT19310RH6P1")
                .accountNo("00")
                .apiID("11")
                .orderCode("12234234")
                .realAmount(10)
                .debitAmount(16)
                .payDate("20200929112923")
                .promotionCode("12")
                .build();
        String isValid = Validation.validate(request);
        Assertions.assertEquals(null, isValid);
    }

    @Test
    void validateAssertNull(){
        Request request = null;
        String isValid = Validation.validate(request);
        Assertions.assertEquals(MessageConfig.MSG_NULL, isValid);
    }

    @Test
    void validateAssertErr(){
        Request request = Request.builder()
                .tokenKey("1601353776839FT19310RH6P1")
                .accountNo("00")
                .orderCode("12234234")
                .realAmount(10)
                .debitAmount(16)
                .payDate("20200929112923")
                .promotionCode("12")
                .build();
        String isValid = Validation.validate(request);
        Assertions.assertEquals(MessageConfig.ERR_API_ID, isValid);
    }
}
