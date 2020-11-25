package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.ShopPayment;
import lombok.Getter;

import java.util.Date;

@Getter
public class ShopPaymentResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private Long shopId;
	private Integer month;
	private String description;
	private Integer originalAmount;
	private Integer comission;
	private Integer totalAmount;
	private Date date;
	private String paymentType;

	//BUILDERS
	public ShopPaymentResponse(ShopPayment shopPayment) {
		if (shopPayment != null) {
			this.id = shopPayment.getId() != null ? shopPayment.getId() : null;
			this.shopId = shopPayment.getShop() != null ? shopPayment.getShop().getId() : null;
			this.month = shopPayment.getMonth() != null ? shopPayment.getMonth() : null;
			this.description = shopPayment.getDescription() != null ? shopPayment.getDescription() : null;
			this.originalAmount = shopPayment.getOriginalAmount() != null ? shopPayment.getOriginalAmount() : null;
			this.comission = shopPayment.getComission() != null ? shopPayment.getComission() : null;
			this.totalAmount = shopPayment.getTotalAmount() != null ? shopPayment.getTotalAmount() : null;
			this.date = shopPayment.getDate() != null ? shopPayment.getDate() : null;
			this.paymentType = shopPayment.getPaymentType() != null ? shopPayment.getPaymentType().toString() : null;
		}
	}

	public ShopPaymentResponse() {
	}

}
