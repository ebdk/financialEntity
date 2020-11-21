package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Card;
import com.uade.financialEntity.models.CardEntity;
import com.uade.financialEntity.models.ShopPromotion;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CardEntityFullResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private List<ShopPromotionResponse> shopPromotions;
	private List<Long> cardIds;
	private String name;
	private String color;
	private Long minimumSalary;
	private Integer minimumDiscount;

	//BUILDERS
	public CardEntityFullResponse(CardEntity cardEntity) {
		if (cardEntity != null) {
			this.id = cardEntity.getId() != null ? cardEntity.getId() : null;
			this.shopPromotions = cardEntity.getShopPromotions() != null
					? cardEntity.getShopPromotions().stream().map(ShopPromotion::toDto).collect(Collectors.toList()) : null;
			this.cardIds = cardEntity.getCards() != null
					? cardEntity.getCards().stream().map(Card::getId).collect(Collectors.toList()) : null;
			this.name = cardEntity.getName() != null ? cardEntity.getName() : null;
			this.color = cardEntity.getColor() != null ? cardEntity.getColor() : null;
			this.minimumSalary = cardEntity.getMinimumSalary() != null ? cardEntity.getMinimumSalary() : null;
			this.minimumDiscount = cardEntity.getMinimumDiscount() != null ? cardEntity.getMinimumDiscount() : null;
		}
	}

	public CardEntityFullResponse() {
	}

}
