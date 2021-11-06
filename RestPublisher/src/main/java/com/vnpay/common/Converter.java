package com.vnpay.common;


import com.vnpay.model.Request;
import com.vnpay.model.RequestVo;

public class Converter {

    /**
     * convert object RequestVo to Request
     * @param msg
     * @return instance of Request
     */
    public static Request convertData(RequestVo msg) {
        return Request.builder()
                .tokenKey(msg.getTokenKey())
                .apiID(msg.getApiID())
                .mobile(msg.getMobile())
                .bankCode(msg.getBankCode())
                .accountNo(msg.getAccountNo())
                .payDate(msg.getPayDate())
                .addtionalData(msg.getAddtionalData())
                .debitAmount(msg.getDebitAmount())
                .respCode(msg.getRespCode())
                .respDesc(msg.getRespDesc())
                .traceTransfer(msg.getTraceTransfer())
                .messageType(msg.getMessageType())
                .checkSum(msg.getCheckSum())
                .orderCode(msg.getOrderCode())
                .userName(msg.getUserName())
                .realAmount(msg.getRealAmount())
                .promotionCode(msg.getPromotionCode())
                .queueNameResponse(msg.getQueueNameResponse())
                .addValue(msg.getAddValue())
                .item(msg.getItem().toString())
                .build();
    }
}
