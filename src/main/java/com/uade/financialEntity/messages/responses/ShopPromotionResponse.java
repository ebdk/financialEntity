package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.ShopPromotion;
import lombok.Getter;

@Getter
public class ShopPromotionResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private ShopResponse shop;
	private CardEntityResponse cardEntity;
	private String description;
	private String day;
	private String productType;
	private Integer percentageValue;

	//BUILDERS
	public ShopPromotionResponse(ShopPromotion shopPromotion) {
		if (shopPromotion != null) {
			this.id = shopPromotion.getId() != null ? shopPromotion.getId() : null;
			this.shop = shopPromotion.getShop() != null ? shopPromotion.getShop().toDto() : null;
			this.cardEntity = shopPromotion.getCardEntity() != null ? shopPromotion.getCardEntity().toDto() : null;
			this.description = shopPromotion.getDescription() != null ? shopPromotion.getDescription() : null;
			this.day = shopPromotion.getDay() != null ? shopPromotion.getDay().toString() : null;
			this.productType = shopPromotion.getProductType() != null ? shopPromotion.getProductType().toString() : null;
			this.percentageValue = shopPromotion.getPercentageValue() != null ? shopPromotion.getPercentageValue() : null;
		}
	}

	public ShopPromotionResponse() {
	}

}
