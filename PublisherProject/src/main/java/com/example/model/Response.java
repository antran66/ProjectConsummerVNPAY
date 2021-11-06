package com.example.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Response {
    private String code;
    private String message;
    private String data;
    private String checksum;
    private String responseId;
}
