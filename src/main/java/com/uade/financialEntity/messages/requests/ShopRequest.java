package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Shop;
import lombok.Getter;

import java.sql.Blob;

@Getter
public class ShopRequest implements Response {

	//ATTRIBUTES
	private String name;
	private Long userId;
	private String imgUrl;
	private Long cuit;
	private Long cbuForBank;

	//BUILDERS
	public Shop toEntity() {
		return new Shop(this);
	}

	public ShopRequest() {
	}

}
