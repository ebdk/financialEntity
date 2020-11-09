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

	public MonthResume getLastMonthResumeClosed() {
		return monthResumes.stream()
				.filter(MonthResume::isClosed)
				.sorted(comparing(MonthResume::getMonthNumber).reversed())
				.collect(toList())
				.get(0);
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
