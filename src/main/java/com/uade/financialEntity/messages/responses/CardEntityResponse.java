package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.CardEntity;
import lombok.Getter;

@Getter
public class CardEntityResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private String name;
	private String color;
	private Long minimumSalary;
	private Integer minimumDiscount;
	private Long limitPay;
	private Long limitMonthlyPay;

	//BUILDERS
	public CardEntityResponse(CardEntity cardEntity) {
		if (cardEntity != null) {
			this.id = cardEntity.getId() != null ? cardEntity.getId() : null;
			this.name = cardEntity.getName() != null ? cardEntity.getName() : null;
			this.color = cardEntity.getColor() != null ? cardEntity.getColor() : null;
			this.minimumSalary = cardEntity.getMinimumSalary() != null ? cardEntity.getMinimumSalary() : null;
			this.minimumDiscount = cardEntity.getMinimumDiscount() != null ? cardEntity.getMinimumDiscount() : null;
			this.limitPay = cardEntity.getLimitPay() != null ? cardEntity.getLimitPay() : null;
			this.limitMonthlyPay = cardEntity.getLimitMonthlyPay() != null ? cardEntity.getLimitMonthlyPay() : null;
		}
	}

	public CardEntityResponse() {
	}

}
