package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.CardEntity;
import lombok.Getter;

@Getter
public class CardEntityRequest implements Response {

	//ATTRIBUTES
	private String name;
	private String imgUrl;

	//BUILDERS
	public CardEntityRequest(CardEntity cardEntity) {
		this.name = cardEntity.getName();
		this.imgUrl = cardEntity.getImgUrl();
	}

	public CardEntityRequest() {
	}

}
