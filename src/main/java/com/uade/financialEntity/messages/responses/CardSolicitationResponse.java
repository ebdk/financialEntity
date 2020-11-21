package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.CardSolicitation;
import lombok.Getter;

@Getter
public class CardSolicitationResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private CustomerResponse customer;
	private CardEntityResponse cardEntity;
	private Boolean accepted;

	//BUILDERS
	public CardSolicitationResponse(CardSolicitation cardSolicitation) {
		if (cardSolicitation != null) {
			this.id = cardSolicitation.getId() != null ? cardSolicitation.getId() : null;
			this.customer = cardSolicitation.getCustomer() != null ? cardSolicitation.getCustomer().toDto() : null;
			this.cardEntity = cardSolicitation.getCardEntity() != null ? cardSolicitation.getCardEntity().toDto() : null;
			this.accepted = cardSolicitation.getAccepted() != null ? cardSolicitation.getAccepted() : null;
		}
	}

	public CardSolicitationResponse() {
	}

}
