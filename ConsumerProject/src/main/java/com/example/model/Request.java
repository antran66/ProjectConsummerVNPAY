package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "TEST_REQUEST_DM")
public class Request implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String tokenKey;
	@Column(nullable = false)
	private String apiID;
	@Column
	private String mobile;
	@Column
	private String bankCode;
	@Column
	private String accountNo;
	@Column
	private String payDate;
	@Column
	private String addtionalData;
	@Column
	private int debitAmount;
	@Column
	private String respCode;
	@Column
	private String respDesc;
	@Column
	private String traceTransfer;
	@Column
	private String messageType;
	@Column
	private String item;
	@Column
	private String checkSum;
	@Column(nullable = false)
	private String orderCode;
	@Column
	private String userName;
	@Column
	private int realAmount;
	@Column
	private String promotionCode;
	@Column
	private String queueNameResponse;
	@Column
	private String addValue;

}
