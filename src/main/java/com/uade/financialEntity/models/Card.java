package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardResponse;
import lombok.Getter;

import javax.persistence.*;

@Entity(name = "Card")
@Table(name = "card")
@Getter
public class Card {

	//ATTRIBUTES
	@Id
	@Column(name = "CARD_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer creditNumber;
	private Integer codeNumber;
	private String validFrom;
	private String goodThrough;
	private String nameCustomer;
	private CardType cardType;
	private Boolean cardPayOnTime;

	public enum CardType {
		BLACK,
		GOLD
	}

	//BUILDERS
	public Card(CardRequest cardRequest) {
		this.creditNumber = cardRequest.getCreditNumber();
		this.codeNumber = cardRequest.getCodeNumber();
		this.validFrom = cardRequest.getValidFrom();
		this.goodThrough = cardRequest.getGoodThrough();
		this.nameCustomer = cardRequest.getNameCustomer();
		this.cardType = CardType.valueOf(cardRequest.getCardType());
		this.cardPayOnTime = cardRequest.getCardPayOnTime();
	}

	public Card() {
	}

	//METHODS
	public CardResponse toDto() {
		return new CardResponse(this);
	}

}
