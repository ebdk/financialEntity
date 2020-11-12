package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.PurchaseItemRequest;
import com.uade.financialEntity.messages.responses.PurchaseItemResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Purchase_Item")
@Table(name = "purchase_item")
@Getter
@Setter
public class PurchaseItem {

	//ATTRIBUTES
	@Id
	@Column(name = "PURCHASE_ITEM_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Purchase purchase;

	private String description;
	private ProductType typeOfProduct;
	private Integer amount;
	private Integer value;

	public enum ProductType {
		GYM,
		COFFE,
		GROCERY,
		ELECTRONICS,
		ALL
	}

	//BUILDERS
	public PurchaseItem(PurchaseItemRequest purchaseItemRequest) {
		this.description = purchaseItemRequest.getDescription();
		this.typeOfProduct = ProductType.valueOf(purchaseItemRequest.getTypeOfProduct());
		this.amount = purchaseItemRequest.getAmount();
		this.value = purchaseItemRequest.getValue();
	}

	public PurchaseItem() {
	}

	//METHODS
	public PurchaseItemResponse toDto() {
		return new PurchaseItemResponse(this);
	}

	public Integer calculateTotalAmount() {
		return amount * value;
	}

}
