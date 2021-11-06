package com.vnpay.common;

/**
 * common message of application
 */
public class MessageConfig {
    public static final String BASE_URI = "http://localhost:8080/";
    public static final String QUEUE = "test_queue2";
    public static final String PATH_FILE_CONFIG =  "\\file-config\\config.properties";
    public static final String EXCHANGE = "test_exchange";
    public static final String ROUTING_KEY = "test_routingKey2";

    public static final String ADD_VALUE = "{\"payMethod\":\"01\",\"payMethodMMS\":1}";
    public static final String CONTINUE_PROCESS = "Request continue process";
    public static final String CODE_FAIL = "01";
    public static final String CODE_SUCCESS = "00";
    public static final String SUCCESS = "Success";
    public static final String FAIL = "Fail";
    public static final String MSG_NULL = "Message request is null";

    public static final String ERR_API_ID = "ApiID can not be null";
    public static final String ERR_ORDER_CODE = "OrderCode can not be null";
    public static final String ERR_DEBIT_AMOUNT = "RealAmount need to bigger than DebitAmount";
    public static final String ERR_PROMOTION_CODE = "PromotionCode can not be null";
    public static final String ERR_TOKEN_KEY = "TokenKey is unique for each day";
    public static final String ERR_FORMAT_DATE = "Date format is wrong";
}
