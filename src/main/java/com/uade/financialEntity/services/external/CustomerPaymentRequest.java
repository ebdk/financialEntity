package com.uade.financialEntity.services.external;

import lombok.Getter;

@Getter
public class CustomerPaymentRequest {

	private Integer amount;
	private String cbu;
	private String provider_code;

	public CustomerPaymentRequest(Integer amount, String cbu, String provider_code) {
		this.amount = amount;
		this.cbu = cbu;
		this.provider_code = provider_code;
	}

	public CustomerPaymentRequest() {
	}
}

