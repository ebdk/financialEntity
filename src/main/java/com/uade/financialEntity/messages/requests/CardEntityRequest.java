package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.CardEntity;
import com.uade.financialEntity.models.Customer;
import lombok.Getter;

import java.sql.Blob;

@Getter
public class CardEntityRequest implements Response {

	//ATTRIBUTES
	private String name;
	private String color;
	private Long minimumSalary;
	private Integer minimumDiscount;
	private Long limitPay;
	private Long limitMonthlyPay;

	//BUILDERS
	public CardEntityRequest() {
	}

	public CardEntity toEntity() {
		return new CardEntity(this);
	}

}
