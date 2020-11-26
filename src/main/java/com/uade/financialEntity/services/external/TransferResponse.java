package com.uade.financialEntity.services.external;

import lombok.Getter;

@Getter
public class TransferResponse {

	private String source_account;
	private String destination_account;
	private Integer amount;
	private String detail_source_account;
	private String detail_destination_account;
	private Long source_reference_number;

	public TransferResponse() {
	}

	public TransferResponse(String source_account, String destination_account, Integer amount, String detail_source_account, String detail_destination_account, Long source_reference_number) {
		this.source_account = source_account;
		this.destination_account = destination_account;
		this.amount = amount;
		this.detail_source_account = detail_source_account;
		this.detail_destination_account = detail_destination_account;
		this.source_reference_number = source_reference_number;
	}
}

