package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Card;
import lombok.Getter;

@Getter
public class CardRequest implements Response {

	//ATTRIBUTES
	private Long cardEntityId;
	private Long customerId;
	private Long creditNumber;
	private Integer secretCode;
	private String validFrom;
	private String goodThrough;
	private String nameCustomer;

	//BUILDERS
	public CardRequest() {
	}

}
