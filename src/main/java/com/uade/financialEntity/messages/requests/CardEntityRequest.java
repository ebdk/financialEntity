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
	private Blob imgUrl;

	//BUILDERS
	public CardEntityRequest(CardEntity cardEntity) {
		this.name = cardEntity.getName();
		this.imgUrl = cardEntity.getImgUrl();
	}

	public CardEntityRequest() {
	}

	public CardEntity toEntity() {
		return new CardEntity(this);
	}

}
