package com.vnpay.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class RequestVo implements Serializable {
	private static final long serialVersionUID = 1L;
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
	private List<Item> item;
	private String checkSum;
	private String orderCode;
	private String userName;
	private int realAmount;
	private String promotionCode;
	private String queueNameResponse;
	private String addValue;

}
