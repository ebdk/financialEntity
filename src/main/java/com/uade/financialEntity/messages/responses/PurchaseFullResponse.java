package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Purchase;
import lombok.Getter;

@Getter
public class PurchaseFullResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private ShopPromotionResponse shopPromotion;
	private ShopResponse shop;
	private MonthResumeResponse monthResume;
	//private List<PurchaseItemResponse> purchaseItems;
	private Integer originalAmount;
	private Integer discount;
	private Integer totalAmount;
	private String date;
	private Integer monthPays;
	private Integer monthsPaid;
	private String description;

	//BUILDERS
	public PurchaseFullResponse(Purchase purchase) {
		if (purchase != null) {
			this.id = purchase.getId() != null ? purchase.getId() : null;
			this.shopPromotion = purchase.getShopPromotion() != null ? purchase.getShopPromotion().toDto() : null;
			this.monthResume = purchase.getMonthResume() != null ? purchase.getMonthResume().toDto() : null;
			/*
			this.purchaseItems = purchase.getPurchaseItems() != null
					? purchase.getPurchaseItems().stream().map(PurchaseItem::toDto).collect(Collectors.toList()) : null;

			 */
			this.originalAmount = purchase.getOriginalAmount() != null ? purchase.getOriginalAmount() : null;
			this.discount = purchase.getDiscount() != null ? purchase.getDiscount() : null;
			this.totalAmount = purchase.getTotalAmount() != null ? purchase.getTotalAmount() : null;
			this.date = purchase.getDate() != null ? purchase.getDate().toString() : null;
			this.monthPays = purchase.getMonthPays() != null ? purchase.getMonthPays() : null;
			this.monthsPaid = purchase.getMonthsPaid() != null ? purchase.getMonthsPaid() : null;
			this.description = purchase.getDescription() != null ? purchase.getDescription() : null;
		}
	}

	public PurchaseFullResponse() {
	}

}
