package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Card;
import lombok.Getter;

@Getter
public class CardResponse implements Response {

	//ATTRIBUTESo
	private Long id;

	private Integer creditNumber;
	private Integer codeNumber;
	private String validFrom;
	private String goodThrough;
	private String nameCustomer;
	private String cardType;
	private Boolean cardPayOnTime;

	//BUILDERS
	public CardResponse(Card card) {
		if (card != null) {
			this.id = card.getId() != null ? card.getId() : null;
			this.creditNumber = card.getCreditNumber() != null ? card.getCreditNumber() : null;
			this.codeNumber = card.getCodeNumber() != null ? card.getCodeNumber() : null;
			this.validFrom = card.getValidFrom() != null ? card.getValidFrom() : null;
			this.goodThrough = card.getGoodThrough() != null ? card.getGoodThrough() : null;
			this.nameCustomer = card.getNameCustomer() != null ? card.getNameCustomer() : null;
			this.cardType = card.getCardType() != null ? card.getCardType().toString() : null;
			this.cardPayOnTime = card.getCardPayOnTime() != null ? card.getCardPayOnTime() : null;

		}
	}

	public CardResponse() {
	}

}
