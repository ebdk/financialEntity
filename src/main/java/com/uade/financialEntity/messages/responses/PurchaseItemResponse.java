/*package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.PurchaseItem;
import lombok.Getter;

@Getter
public class PurchaseItemResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private String description;
	private String typeOfProduct;
	private Integer amount;
	private Integer value;

	//BUILDERS
	public PurchaseItemResponse(PurchaseItem purchaseItem) {
		if (purchaseItem != null) {
			this.id = purchaseItem.getId() != null ? purchaseItem.getId() : null;
			this.description = purchaseItem.getDescription() != null ? purchaseItem.getDescription() : null;
			this.typeOfProduct = purchaseItem.getTypeOfProduct() != null ? purchaseItem.getTypeOfProduct().toString() : null;
			this.amount = purchaseItem.getAmount() != null ? purchaseItem.getAmount() : null;
			this.value = purchaseItem.getValue() != null ? purchaseItem.getValue() : null;
		}
	}

	public PurchaseItemResponse() {
	}

}

 */
