package com.vnpay.common;

/**
 * common message for application
 */
public class MessageConfig {
    public static final String URL_FOODBOOK = "https://api.foodbook.vn/ipos/ws/xpartner/callback/vnpay";
    public static final String URL_TEST_HTTP_CODE = "http://localhost:8090/http/ok";
    public static final String PATH_FILE_CONFIG =  "\\file-config\\config.properties";
    public static final String QUEUE = "test_queue2";
    public static final String EXCHANGE = "test_exchange2";
    public static final String ROUTING_KEY = "test_routingKey2";
    public static final String SQL_INSERT = "INSERT INTO TEST_REQUEST_DM (ACCOUNT_NO, ADD_VALUE, ADDTIONAL_DATA, APIID, BANK_CODE, CHECK_SUM, DEBIT_AMOUNT, ITEM, MESSAGE_TYPE, MOBILE, ORDER_CODE, PAY_DATE, PROMOTION_CODE, QUEUE_NAME_RESPONSE, REAL_AMOUNT, RESP_CODE, RESP_DESC, TOKEN_KEY, TRACE_TRANSFER, USER_NAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SUCCESS = "Success";
    public static final String FAIL_TO_GET_REDIS = "Fail to get redis from pool";
    public static final String FAIL_TO_SAVE = "Fail when save to database";
    public static final String FAIL_HTTP = "Fail to send http request";
    public static final int SECOND_TO_LIVE = 86400;

    public static final String ERR_TOKEN_KEY = "TokenKey is unique for each day";
}
