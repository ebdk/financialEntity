package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Shop;
import com.uade.financialEntity.models.ShopPayment;
import com.uade.financialEntity.models.ShopPromotion;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Getter
public class ShopFullResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private UserResponse user;
	private Map<Integer, Map<String, List<ShopPaymentResponse>>> purchasesMap;
	private List<ShopPromotionResponse> promotions;
	private String name;
	private String cuit;
	private String cbuForBank;

	//BUILDERS
	public ShopFullResponse(Shop shop) {
		if (shop != null) {
			this.id = shop.getId() != null ? shop.getId() : null;
			this.user = shop.getUser() != null ? shop.getUser().toDto() : null;

			List<ShopPaymentResponse> purchases = shop.getPurchases() != null
					? shop.getPurchases()
					.stream()
					.map(ShopPayment::toDto)
					.collect(Collectors.toList()) : null;

			if (purchases != null) {
				Map<Integer, List<ShopPaymentResponse>> map = purchases
						.stream()
						.collect(groupingBy(ShopPaymentResponse::getMonth));

				Map<Integer,
						Map<String, List<ShopPaymentResponse>>> complexMap = new HashMap<>();

				map.keySet().forEach(monthNumber -> {
					List<ShopPaymentResponse> paymentsInMonth = map.get(monthNumber);

					Map<String, List<ShopPaymentResponse>> orderedPaymentsInMonth = paymentsInMonth
							.stream()
							.collect(groupingBy(ShopPaymentResponse::getPaymentType));

					complexMap.put(monthNumber, orderedPaymentsInMonth);
				});

				this.purchasesMap = complexMap;
			}

			this.promotions = shop.getShopPromotions() != null
					? shop.getShopPromotions().stream().map(ShopPromotion::toDto).collect(Collectors.toList()) : null;
			this.name = shop.getName() != null ? shop.getName() : null;
			this.cuit = shop.getCuit() != null ? shop.getCuit() : null;
			this.cbuForBank = shop.getCbuForBank() != null ? shop.getCbuForBank() : null;
		}
	}

	public ShopFullResponse() {
	}

}
