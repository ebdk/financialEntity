package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.ShopPromotionRequest;
import com.uade.financialEntity.messages.responses.ShopPromotionResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Shop_Promotion")
@Table(name = "shop_promotion")
@Getter
@Setter
public class ShopPromotion {

	//ATTRIBUTES
	@Id
	@Column(name = "SHOP_PROMOTION_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String description;
	private PromotionDay day;
	private PurchaseItem.ProductType productType;
	private Integer percentageValue;

	public enum PromotionDay {
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY
	}

	//BUILDERS
	public ShopPromotion(ShopPromotionRequest userRequest) {
		this.description = userRequest.getDescription();
		this.day = PromotionDay.valueOf(userRequest.getDay());
		this.productType = PurchaseItem.ProductType.valueOf(userRequest.getProductType());
		this.percentageValue = userRequest.getPercentageValue();
	}

	public ShopPromotion() {
	}

	//METHODS
	public ShopPromotionResponse toDto() {
		return new ShopPromotionResponse(this);
	}

}
