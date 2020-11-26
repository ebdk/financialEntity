package com.uade.financialEntity.services.external;

import lombok.Getter;

@Getter
public class TransferRequest {

	private Integer amount;
	private String cbu;
	private String detail;
	private String provider_code;

	public TransferRequest(Integer amount, String cbu, String detail, String provider_code) {
		this.amount = amount;
		this.cbu = cbu;
		this.detail = detail;
		this.provider_code = provider_code;
	}

	public TransferRequest() {
	}
}

