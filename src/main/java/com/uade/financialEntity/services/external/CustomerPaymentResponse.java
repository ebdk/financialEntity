package com.uade.financialEntity.services.external;

import lombok.Getter;

@Getter
public class CustomerPaymentResponse {

	private String source_account;
	private String destination_account;
	private Integer amount;
	private String detail_source_account;
	private String detail_destination_account;
	private Long destination_reference_number;

	public CustomerPaymentResponse() {
	}

	public CustomerPaymentResponse(String source_account, String destination_account, Integer amount, String detail_source_account, String detail_destination_account, Long destination_reference_number) {
		this.source_account = source_account;
		this.destination_account = destination_account;
		this.amount = amount;
		this.detail_source_account = detail_source_account;
		this.detail_destination_account = detail_destination_account;
		this.destination_reference_number = destination_reference_number;
	}
}

