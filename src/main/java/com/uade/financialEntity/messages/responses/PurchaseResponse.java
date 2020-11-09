package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Purchase;
import lombok.Getter;

@Getter
public class PurchaseResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private String description;
	private Integer totalAmount;
	private String date;
	private Integer monthPays;
	private Integer monthsPaid;

	//BUILDERS
	public PurchaseResponse(Purchase purchase) {
		if (purchase != null) {
			this.id = purchase.getId() != null ? purchase.getId() : null;
			this.description = purchase.getDescription() != null ? purchase.getDescription() : null;
			this.totalAmount = purchase.getTotalAmount() != null ? purchase.getTotalAmount() : null;
			this.date = purchase.getDate() != null ? purchase.getDate().toString() : null;
			//this.monthPays = purchase.getMonthPays() != null ? purchase.getMonthPays() : null;
			//this.monthsPaid = purchase.getMonthsPaid() != null ? purchase.getMonthsPaid() : null;
		}
	}

	public PurchaseResponse() {
	}

}
