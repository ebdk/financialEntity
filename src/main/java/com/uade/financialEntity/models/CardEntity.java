package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.CardEntityRequest;
import com.uade.financialEntity.messages.responses.CardEntityResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "CardEntity")
@Table(name = "card_entity")
@Getter
@Setter
public class CardEntity {

	//ATTRIBUTES
	@Id
	@Column(name = "CARD_ENTITY_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "cardEntity", cascade = CascadeType.ALL)
	private List<ShopPromotion> shopPromotions;

	@OneToMany(mappedBy = "cardEntity", cascade = CascadeType.ALL)
	private List<Card> cards;

	private String name;
	private String imgUrl;

	//BUILDERS
	public CardEntity(CardEntityRequest cardEntityRequest) {
		this.name = cardEntityRequest.getName();
		this.imgUrl = cardEntityRequest.getImgUrl();
	}

	public CardEntity() {
	}

	//METHODS
	public CardEntityResponse toDto() {
		return new CardEntityResponse(this);
	}

}
