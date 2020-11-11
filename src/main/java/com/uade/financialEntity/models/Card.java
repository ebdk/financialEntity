package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardFullResponse;
import com.uade.financialEntity.messages.responses.CardResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Entity(name = "Card")
@Table(name = "card")
@Getter
@Setter
public class Card {

	//ATTRIBUTES
	@Id
	@Column(name = "CARD_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Customer customer;

	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
	private List<MonthResume> monthResumes;

	@ManyToOne
	private CardEntity cardEntity;

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
		this.creditNumber = cardRequest.getCreditNumber() != null ? cardRequest.getCreditNumber() : creditNumber;
		this.codeNumber = cardRequest.getCodeNumber() != null ? cardRequest.getCodeNumber() : codeNumber;
		this.validFrom = cardRequest.getValidFrom() != null ? cardRequest.getValidFrom() : validFrom;
		this.goodThrough = cardRequest.getGoodThrough() != null ? cardRequest.getGoodThrough() : goodThrough;
		this.nameCustomer = cardRequest.getNameCustomer() != null ? cardRequest.getNameCustomer() : nameCustomer;
		this.cardType = cardRequest.getCardType() != null ? CardType.valueOf(cardRequest.getCardType()) : cardType;
		this.cardPayOnTime = cardRequest.getCardPayOnTime() != null ? cardRequest.getCardPayOnTime() : cardPayOnTime;
	}

	public Card() {
	}

	//METHODS
	public CardResponse toDto() {
		return new CardResponse(this);
	}

	public CardFullResponse toFullDto() {
		return new CardFullResponse(this);
	}

	public MonthResume getLastMonthResumeOpen() {
		return monthResumes.stream()
				.filter(MonthResume::isOpen)
				.sorted(comparing(MonthResume::getMonthNumber).reversed())
				.collect(toList())
				.get(0);
	}

	public void modify(CardRequest cardRequest) {
		this.creditNumber = cardRequest.getCreditNumber() != null ? cardRequest.getCreditNumber() : creditNumber;
		this.codeNumber = cardRequest.getCodeNumber() != null ? cardRequest.getCodeNumber() : codeNumber;
		this.validFrom = cardRequest.getValidFrom() != null ? cardRequest.getValidFrom() : validFrom;
		this.goodThrough = cardRequest.getGoodThrough() != null ? cardRequest.getGoodThrough() : goodThrough;
		this.nameCustomer = cardRequest.getNameCustomer() != null ? cardRequest.getNameCustomer() : nameCustomer;
		this.cardType = cardRequest.getCardType() != null ? CardType.valueOf(cardRequest.getCardType()) : cardType;
		this.cardPayOnTime = cardRequest.getCardPayOnTime() != null ? cardRequest.getCardPayOnTime() : cardPayOnTime;
	}

	public String getCardEntityName() {
		return cardEntity.getName();
	}

	public List<Purchase> getPurchasesRemainingMonthPay() {
		List<Purchase> purchases = new ArrayList<>();
		monthResumes.forEach(monthResume -> purchases.addAll(monthResume.getPurchasesRemainingMonthPay()));
		return purchases;
	}

	public List<Purchase> clonePurchase(List<Purchase> originalPurchases) {
		List<Purchase> newPurchases = new ArrayList<>();
		originalPurchases.forEach(purchase -> newPurchases.add(purchase.cloneNew()));
		return newPurchases;
	}

}
