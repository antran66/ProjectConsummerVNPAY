package com.vnpay.common;

import com.vnpay.model.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.charset.StandardCharsets;

public class CommonTest {

    @Test
    void setMessageFromCode404() {
        String message = Common.setMessageFromCode(404);
        Assertions.assertEquals(MessageHTTPClient.NOT_FOUND, message);
    }

    @Test
    void setMessageFromCode200() {
        String message = Common.setMessageFromCode(200);
        Assertions.assertEquals(MessageHTTPClient.SUCCESS, message);
    }

    @Test
    void setMessageFromCode201() {
        String message = Common.setMessageFromCode(201);
        Assertions.assertEquals(MessageHTTPClient.CREATED, message);
    }

    @Test
    void setMessageFromCode204() {
        String message = Common.setMessageFromCode(204);
        Assertions.assertEquals(MessageHTTPClient.NO_CONTENT, message);
    }

    @Test
    void setMessageFromCode100() {
        String message = Common.setMessageFromCode(100);
        Assertions.assertEquals(MessageHTTPClient.CONTINUE, message);
    }

    @Test
    void setMessageFromCode500() {
        String message = Common.setMessageFromCode(500);
        Assertions.assertEquals(MessageHTTPClient.SERVER_ERR, message);
    }

    @Test
    void setMessageFromCode400() {
        String message = Common.setMessageFromCode(400);
        Assertions.assertEquals(MessageHTTPClient.UNAUTHORIZED, message);
    }

    @Test
    void deserializeTrue() {
        try {
            Request request = Request.builder()
                    .tokenKey("1601353776839FT19310RH6P1")
                    .build();
            byte[] byteObject = getByteArray(request);
            Request result = (Request) Common.deserialize(byteObject);

            Assertions.assertEquals(request, result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deserializeFalse() {
        try {
            String message = "Test False";
            byte[] byteObject = message.getBytes(StandardCharsets.UTF_8);
            Request result = (Request) Common.deserialize(byteObject);

            Assertions.assertEquals(new StreamCorruptedException(), result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private byte[] getByteArray(Request request) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(request);
        return out.toByteArray();
    }
}
