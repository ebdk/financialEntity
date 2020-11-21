package com.uade.financialEntity.services.external;

import lombok.Getter;

@Getter
public class CbuValidationResponse {

	private Long cbu;
	private Boolean isBankrupt;
	private Boolean failed;

}
