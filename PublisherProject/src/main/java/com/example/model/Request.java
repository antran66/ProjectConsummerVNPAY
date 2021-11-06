package com.example.model;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Request implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private String tokenKey;
	private String apiID;
	private String mobile;
	private String bankCode;
	private String accountNo;
	private String payDate;
	private String addtionalData;
	private int debitAmount;
	private String respCode;
	private String respDesc;
	private String traceTransfer;
	private String messageType;
	private String item;
	private String checkSum;
	private String orderCode;
	private String userName;
	private int realAmount;
	private String promotionCode;
	private String queueNameResponse;
	private String addValue;
}
