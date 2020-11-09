package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.responses.MonthlyExpenseResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "MonthlyExpense")
@Table(name = "monthly_expense")
@Getter
@Setter
public class MonthlyExpense {

	//ATTRIBUTES
	@Id
	@Column(name = "MONTHLY_EXPENSE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Card card;

	private Integer amount;
	private Integer monthPays;
	private Integer monthsPaid;
	private String details;

	//BUILDERS
	public MonthlyExpense() {
	}

	//METHODS
	public MonthlyExpenseResponse toDto() {
		return new MonthlyExpenseResponse(this);
	}

	public boolean isFullyPaid() {
		return monthsPaid >= monthPays;
	}

	public boolean isNotFullyPaid() {
		return !isFullyPaid();
	}

	public void increaseIfNotFullyPaid() {
		monthsPaid = isFullyPaid() ? monthPays : monthsPaid + 1;
	}

}
