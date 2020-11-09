package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Purchase;
import lombok.Getter;

@Getter
public class PurchaseRequest implements Response {

	//ATTRIBUTES
	private String description;
	private Integer totalAmount;
	private String date;
	private Integer monthPays;
	private Integer monthsPaid;

	//BUILDERS
	public PurchaseRequest(Purchase purchase) {
		this.description = purchase.getDescription();
		this.totalAmount = purchase.getTotalAmount();
		this.date = purchase.getDate().toString();
		//this.monthPays = purchase.getMonthPays();
		//this.monthsPaid = purchase.getMonthsPaid();
	}

	public PurchaseRequest() {
	}

}
