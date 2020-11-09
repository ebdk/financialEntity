package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.MonthResumeRequest;
import com.uade.financialEntity.messages.responses.MonthResumeFullResponse;
import com.uade.financialEntity.messages.responses.MonthResumeResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "MonthResume")
@Table(name = "month_resume")
@Getter
@Setter
public class MonthResume {

	//ATTRIBUTES
	@Id
	@Column(name = "MONTH_RESUME_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Card card;

	@OneToMany(mappedBy = "monthResume", cascade = CascadeType.ALL)
	private List<Purchase> purchases;

	private Integer monthNumber;
	private Integer amountToPay;
	private Boolean open;
	private Integer amountPaid;

	//BUILDERS
	public MonthResume(MonthResumeRequest monthResumeRequest) {
		this.monthNumber = monthResumeRequest.getMonthNumber();
		this.amountToPay = monthResumeRequest.getAmountToPay();
		this.open = monthResumeRequest.getOpen();
		this.amountPaid = monthResumeRequest.getAmountPaid();
	}

	public MonthResume() {
		open = true;
	}

	public void addPurchase(Purchase purchase) {
		this.purchases.add(purchase);
	}

	//METHODS
	public MonthResumeResponse toDto() {
		return new MonthResumeResponse(this);
	}

	public MonthResumeFullResponse toFullDto() {
		return new MonthResumeFullResponse(this);
	}

	boolean isClosed() {
		return !open;
	}

	boolean isOpen() {
		return open;
	}

	public Integer calculateTotalAmount() {
		return purchases.stream().mapToInt(Purchase::getTotalAmount).sum();
	}

	/*
	public List<Purchase> getPurchasesRemainingMonthPay() {
		return purchases.stream()
				.filter(Purchase::isNotFullyPaid)
				.collect(toList());
	}
	 */

	public boolean paidCorrectly() {
		return amountPaid >= amountToPay;
	}

	public void close() {
		open = false;
	}

}
