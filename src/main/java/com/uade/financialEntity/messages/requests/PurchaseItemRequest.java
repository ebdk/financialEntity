/*package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.PurchaseItem;
import lombok.Getter;

@Getter
public class PurchaseItemRequest implements Response {

	//ATTRIBUTES
	private String description;
	private String typeOfProduct;
	private Integer amount;
	private Integer value;

	//BUILDERS
	public PurchaseItemRequest(PurchaseItem purchaseItem) {
		this.description = purchaseItem.getDescription();
		this.typeOfProduct = purchaseItem.getTypeOfProduct().toString();
		this.amount = purchaseItem.getAmount();
		this.value = purchaseItem.getValue();
	}

	public PurchaseItemRequest() {
	}


	//METHODS
	public PurchaseItem toEntity() {
		return new PurchaseItem(this);
	}

}
 */
