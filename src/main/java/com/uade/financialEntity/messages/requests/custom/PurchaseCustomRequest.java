package com.uade.financialEntity.messages.requests.custom;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.PurchaseItemRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class PurchaseCustomRequest implements Response {

	//ATTRIBUTES
	private Long shopId;
	private Long cardId;
	private Integer monthPays;
	private List<PurchaseItemRequest> purchaseItems;

	//BUILDERS
	public PurchaseCustomRequest() {
	}

}
