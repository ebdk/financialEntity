package com.uade.financialEntity.messages.requests.custom;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.requests.ShopPromotionRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class ThirdInsertRequest implements Response {

	//ATTRIBUTES
	private List<ShopPromotionRequest> shopPromotionRequests;
	private List<CardRequest> cardRequests;

	//BUILDERS
	public ThirdInsertRequest() {
	}

}
