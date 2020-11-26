package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Shop;
import com.uade.financialEntity.models.ShopPayment;
import com.uade.financialEntity.models.ShopPromotion;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ShopResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private Long userId;
	//private List<Long> purchasesIds;
	private List<Long> promotionsIds;
	private String name;
	private String cuit;
	private String cbu;

	//BUILDERS
	public ShopResponse(Shop shop) {
		if (shop != null) {
			this.id = shop.getId() != null ? shop.getId() : null;
			this.userId = shop.getUser() != null ? shop.getUser().getId() : null;
			/*this.purchasesIds = shop.getPurchases() != null
					? shop.getPurchases().stream().map(ShopPayment::getId).collect(Collectors.toList()) : null;
			 */
			this.promotionsIds = shop.getShopPromotions() != null
					? shop.getShopPromotions().stream().map(ShopPromotion::getId).collect(Collectors.toList()) : null;
			this.name = shop.getName() != null ? shop.getName() : null;
			this.cuit = shop.getCuit() != null ? shop.getCuit() : null;
			this.cbu = shop.getCbuForBank() != null ? shop.getCbuForBank() : null;
		}
	}

	public ShopResponse() {
	}

}
