package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Card;
import lombok.Getter;

@Getter
public class CardRequest implements Response {

	//ATTRIBUTES
	private Long cardEntityId;
	private Long customerId;
	private Integer creditNumber;
	private Integer codeNumber;
	private String validFrom;
	private String goodThrough;
	private String nameCustomer;
	private String cardType;
	private Boolean cardPayOnTime;

	//BUILDERS
	public CardRequest(Card card) {
		this.creditNumber = card.getCreditNumber();
		this.codeNumber = card.getCodeNumber();
		this.validFrom = card.getValidFrom();
		this.goodThrough = card.getGoodThrough();
		this.nameCustomer = card.getNameCustomer();
		this.cardType = card.getCardType().toString();
		this.cardPayOnTime = card.getCardPayOnTime();
	}

	public CardRequest() {
	}

}
