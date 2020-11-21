package com.uade.financialEntity.messages.requests.custom;

import com.uade.financialEntity.messages.Response;
import lombok.Getter;

@Getter
public class PurchaseCustomRequest implements Response {

	//ATTRIBUTES
	private String shopName;
	private Long creditNumber;
	private Integer secretCode;
	private Integer monthPays;
	private Integer amount;
	private Boolean productWithDiscount;
	//private List<PurchaseItemRequest> purchaseItems;

	//BUILDERS
	public PurchaseCustomRequest() {
	}

}
