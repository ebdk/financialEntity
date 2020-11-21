package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.responses.ShopFullResponse;
import com.uade.financialEntity.messages.responses.ShopResponse;
import com.uade.financialEntity.models.ShopPromotion.PromotionDay;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Entity(name = "Shop")
@Table(name = "shop")
@Getter
@Setter
public class Shop {

	//ATTRIBUTES
	@Id
	@Column(name = "SHOP_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
	private List<ShopPayment> purchases;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
	private List<ShopPromotion> shopPromotions;

	@OneToOne(cascade = {CascadeType.ALL})
	private User user;

	private String name;
	private String imgUrl;
	private Long cuit;
	private Long cbuForBank;

	//BUILDERS
	public Shop(ShopRequest request) {
		this.name = request.getName() != null ? request.getName() : name;
		this.imgUrl = request.getImgUrl() != null ? request.getImgUrl() : imgUrl;
		this.cuit = request.getCuit() != null ? request.getCuit() : cuit;
		this.cbuForBank = request.getCbuForBank() != null ? request.getCbuForBank() : cbuForBank;
	}

	public Shop() {
	}

	public Shop(String name) {
		this.name = name;
	}

	//METHODS
	public ShopResponse toDto() {
		return new ShopResponse(this);
	}

	public ShopFullResponse toFullDto() {
		return new ShopFullResponse(this);
	}

	public ShopPromotion getPromotion(String cardEntityName, Boolean productWithDiscount, String date) {
		PromotionDay day = PromotionDay.valueOf(date.toUpperCase());
		List<ShopPromotion> shopPromotionList = shopPromotions.stream()
				.filter(shopPromotion -> shopPromotion.isPromotion(cardEntityName, productWithDiscount, day))
				.sorted(comparing(ShopPromotion::getPercentageValue).reversed())
				.collect(toList());
		if (!shopPromotionList.isEmpty()) {
			return shopPromotionList.get(0);
		} else {
			return null;
		}
	}

	/*
	public ShopPromotion getPromotion(String cardEntityName, List<PurchaseItem.ProductType> purchaseProductTypes, String date) {

		PromotionDay day = PromotionDay.valueOf(date.toUpperCase());
		List<ShopPromotion> shopPromotionList = shopPromotions.stream()
				.filter(shopPromotion -> shopPromotion.isPromotion(cardEntityName, purchaseProductTypes, day))
				.sorted(comparing(ShopPromotion::getPercentageValue).reversed())
				.collect(toList());
		if (!shopPromotionList.isEmpty()) {
			return shopPromotionList.get(0);
		} else {
			return null;
		}
	}
	*/

	public void modify(ShopRequest request) {
		this.name = request.getName() != null ? request.getName() : name;
		this.imgUrl = request.getImgUrl() != null ? request.getImgUrl() : imgUrl;
		this.cuit = request.getCuit() != null ? request.getCuit() : cuit;
		this.cbuForBank = request.getCbuForBank() != null ? request.getCbuForBank() : cbuForBank;
	}
}
