package com.example.common;

import com.example.model.Request;
import org.apache.logging.log4j.util.Strings;

public class Validation {

    public static String validate(Request request) {
        if (null == request) {
            return MessageConfig.MSG_NULL;
        }
        if (Strings.isBlank(request.getApiID())) {
            return MessageConfig.ERR_API_ID;
        }
        if (Strings.isBlank(request.getOrderCode())) {
            return MessageConfig.ERR_ORDER_CODE;
        }
        if (request.getRealAmount() > request.getDebitAmount()) {
            return MessageConfig.ERR_DEBIT_AMOUNT;
        }
        if (!Common.checkDateFormat(request.getPayDate())) {
            return MessageConfig.ERR_FORMAT_DATE;
        }
        if (request.getRealAmount() != request.getDebitAmount()) {
            if (Strings.isBlank(request.getPromotionCode())) {
                return MessageConfig.ERR_PROMOTION_CODE;
            }
        }
        request.setAddValue(MessageConfig.ADD_VALUE);
        return null;
    }
}
