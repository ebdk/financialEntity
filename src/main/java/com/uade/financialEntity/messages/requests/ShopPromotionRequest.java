package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.ShopPromotion;
import lombok.Getter;

@Getter
public class ShopPromotionRequest implements Response {

	//ATTRIBUTES
	private Long shopId;
	private Long cardEntityId;
	private String description;
	private String day;
	private String productType;
	private Integer percentageValue;

	//BUILDERS
	public ShopPromotionRequest(ShopPromotion shopPromotion) {
		this.description = shopPromotion.getDescription();
		this.day = shopPromotion.getDay().toString();
		this.productType = shopPromotion.getProductType().toString();
		this.percentageValue = shopPromotion.getPercentageValue();
	}

	public ShopPromotionRequest() {
	}

	public ShopPromotion toEntity() {
		return new ShopPromotion(this);
	}
}
