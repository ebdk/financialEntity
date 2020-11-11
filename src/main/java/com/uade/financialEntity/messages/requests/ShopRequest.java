package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Shop;
import lombok.Getter;

@Getter
public class ShopRequest implements Response {

	//ATTRIBUTES
	private String name;
	private String imgUrl;

	//BUILDERS
	public Shop toEntity() {
		return new Shop(this);
	}

	public ShopRequest() {
	}

}
