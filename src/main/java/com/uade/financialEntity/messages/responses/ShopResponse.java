package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Shop;
import lombok.Getter;

import java.sql.Blob;

@Getter
public class ShopResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private String name;
	private String imgUrl;

	//BUILDERS
	public ShopResponse(Shop shop) {
		if (shop != null) {
			this.id = shop.getId() != null ? shop.getId() : null;
			this.name = shop.getName() != null ? shop.getName() : null;
			this.imgUrl = shop.getImgUrl() != null ? shop.getImgUrl() : null;
		}
	}

	public ShopResponse() {
	}

}
