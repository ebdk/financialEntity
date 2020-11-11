package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.ShopRequest;
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
	private List<Purchase> purchases;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
	private List<ShopPromotion> shopPromotions;

	private String name;
	private String imgUrl;

	//BUILDERS
	public Shop(ShopRequest shopRequest) {
		this.name = shopRequest.getName();
		this.imgUrl = shopRequest.getImgUrl();
	}

	public Shop() {
	}

	//METHODS
	public ShopResponse toDto() {
		return new ShopResponse(this);
	}

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
}
