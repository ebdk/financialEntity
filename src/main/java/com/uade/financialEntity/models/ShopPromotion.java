package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.ShopPromotionRequest;
import com.uade.financialEntity.messages.responses.ShopPromotionResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.uade.financialEntity.models.ShopPromotion.PromotionDay.ANYDAY;

@Entity(name = "ShopPromotion")
@Table(name = "shop_promotion")
@Getter
@Setter
public class ShopPromotion {

	//ATTRIBUTES
	@Id
	@Column(name = "SHOP_PROMOTION_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private CardEntity cardEntity;

	@ManyToOne
	private Shop shop;

	@OneToMany(mappedBy = "shopPromotion", cascade = CascadeType.ALL)
	private List<Purchase> purchases;

	private String description;
	private PromotionDay day;
	//private ProductType productType;
	private Integer percentageValue;

	public enum PromotionDay {
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY,
		ANYDAY
	}

	//BUILDERS
	public ShopPromotion(ShopPromotionRequest request) {
		this.description = request.getDescription() != null ? request.getDescription() : description;
		this.day = request.getDay() != null ? PromotionDay.valueOf(request.getDay()) : day;
		//this.productType = request.getProductType() != null ? ProductType.valueOf(request.getProductType()) : productType;
		this.percentageValue = request.getPercentageValue() != null ? request.getPercentageValue() : percentageValue;
	}

	public ShopPromotion() {
	}

	//METHODS
	public ShopPromotionResponse toDto() {
		return new ShopPromotionResponse(this);
	}

	public boolean isPromotion(String cardEntityName, Boolean productWithDiscount, PromotionDay date) {

		boolean sameBankEntity = cardEntityName.equals(cardEntity.getName());
		boolean sameDay = ANYDAY.equals(day) || day.equals(date);


		return sameBankEntity && productWithDiscount && sameDay;
	}

	/*
	public boolean isPromotion(String cardEntityName, List<ProductType> purchaseProductTypes, PromotionDay date) {

		boolean sameBankEntity = cardEntityName.equals(cardEntity.getName());
		boolean sameProductType = ProductType.ALL.equals(productType) || purchaseProductTypes.contains(productType);
		boolean sameDay = ANYDAY.equals(day) || day.equals(date);


		return sameBankEntity && sameProductType && sameDay;
	}
	 */

	public void modify(ShopPromotionRequest request) {
		this.description = request.getDescription() != null ? request.getDescription() : description;
		this.day = request.getDay() != null ? PromotionDay.valueOf(request.getDay()) : day;
		//this.productType = request.getProductType() != null ? ProductType.valueOf(request.getProductType()) : productType;
		this.percentageValue = request.getPercentageValue() != null ? request.getPercentageValue() : percentageValue;
	}

}
