package com.vnpay.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Common {

    /**
     * set message response from number code
     *
     * @param code
     * @return
     */
    public static String setMessageFromCode(int code) {
        String result = MessageConfig.FAIL_HTTP;
        switch (code) {
            case MessageHTTPClient.CODE_SUCCESS:
                result = MessageHTTPClient.SUCCESS;
                break;
            case MessageHTTPClient.CODE_CONTINUE:
                result = MessageHTTPClient.CONTINUE;
                break;
            case MessageHTTPClient.CODE_BAD_GATEWAY:
                result = MessageHTTPClient.BAD_GATEWAY;
                break;
            case MessageHTTPClient.CODE_CREATED:
                result = MessageHTTPClient.CREATED;
                break;
            case MessageHTTPClient.CODE_NOT_FOUND:
                result = MessageHTTPClient.NOT_FOUND;
                break;
            case MessageHTTPClient.CODE_NO_CONTENT:
                result = MessageHTTPClient.NO_CONTENT;
                break;
            case MessageHTTPClient.CODE_SERVER_ERR:
                result = MessageHTTPClient.SERVER_ERR;
                break;
            case MessageHTTPClient.CODE_UNAUTHORIZED:
                result = MessageHTTPClient.UNAUTHORIZED;
                break;
        }
        return result;
    }

    /**
     * deserialize byte value to object
     *
     * @param byteArray
     * @return instance of object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] byteArray)
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
