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

	@OneToMany(mappedBy = "cardEntity", cascade = CascadeType.ALL)
	private List<CardSolicitation> cardSolicitations;

	private String name;
	private String color;
	private Long minimumSalary;
	private Integer minimumDiscount;

	//BUILDERS
	public CardEntity(CardEntityRequest request) {
		this.name = request.getName() != null ? request.getName() : name;
		this.color = request.getColor() != null ? request.getColor() : color;
		this.minimumSalary = request.getMinimumSalary() != null ? request.getMinimumSalary() : minimumSalary;
		this.minimumDiscount = request.getMinimumDiscount() != null ? request.getMinimumDiscount() : minimumDiscount;
	}

	public CardEntity() {
	}

	//METHODS
	public CardEntityResponse toDto() {
		return new CardEntityResponse(this);
	}

	public void modify(CardEntityRequest request) {
		this.name = request.getName() != null ? request.getName() : name;
		this.color = request.getColor() != null ? request.getColor() : color;
		this.minimumSalary = request.getMinimumSalary() != null ? request.getMinimumSalary() : minimumSalary;
		this.minimumDiscount = request.getMinimumDiscount() != null ? request.getMinimumDiscount() : minimumDiscount;
	}

	public boolean isAvailableForSalary(Long salary) {
		return this.minimumSalary <= salary;
	}

}
