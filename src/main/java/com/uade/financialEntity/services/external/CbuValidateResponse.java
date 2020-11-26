package com.uade.financialEntity.services.external;

import lombok.Getter;

@Getter
public class CbuValidateResponse {

	private Long id;
	private String name;
	private String last_name;
	private String dni;
	private String cuil;
	private String email;
	private Boolean active;
	private String message;

	public CbuValidateResponse() {
	}

}

