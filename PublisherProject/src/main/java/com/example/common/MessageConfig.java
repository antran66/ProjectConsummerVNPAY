package com.example.common;

public class MessageConfig {
    public static final String QUEUE = "gtgt_queue";
    public static final String EXCHANGE = "gtgt_exchange";
    public static final String ROUTING_KEY = "gtgt_routingKey";

    public static final String ADD_VALUE = "{\"payMethod\":\"01\",\"payMethodMMS\":1}";
    public static final String CODE_FAIL = "01";
    public static final String MSG_NULL = "Message request is null";

    public static final String ERR_API_ID = "ApiID can not be null";
    public static final String ERR_ORDER_CODE = "OrderCode can not be null";
    public static final String ERR_DEBIT_AMOUNT = "RealAmount need to bigger than DebitAmount";
    public static final String ERR_PROMOTION_CODE = "PromotionCode can not be null";
    public static final String ERR_TOKEN_KEY = "TokenKey is unique for each day";
    public static final String ERR_FORMAT_DATE = "Date format is wrong";
}
