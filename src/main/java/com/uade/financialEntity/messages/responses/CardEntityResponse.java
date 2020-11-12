package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.CardEntity;
import lombok.Getter;

@Getter
public class CardEntityResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private String name;
	private String imgUrl;

	//BUILDERS
	public CardEntityResponse(CardEntity cardEntity) {
		if (cardEntity != null) {
			this.id = cardEntity.getId() != null ? cardEntity.getId() : null;
			this.name = cardEntity.getName() != null ? cardEntity.getName() : null;
			this.imgUrl = cardEntity.getImgUrl() != null ? cardEntity.getImgUrl() : null;
		}
	}

	public CardEntityResponse() {
	}

}
