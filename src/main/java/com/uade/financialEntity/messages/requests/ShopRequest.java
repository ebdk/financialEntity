package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Shop;
import lombok.Getter;

@Getter
public class ShopRequest implements Response {

	//ATTRIBUTES
	private String name;

	//BUILDERS
	public ShopRequest(Shop shop) {
		this.name = shop.getName();
	}

	public ShopRequest() {
	}

}
