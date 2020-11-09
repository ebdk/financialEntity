package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.MonthlyExpense;
import lombok.Getter;

@Getter
public class MonthlyExpenseResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private Integer amount;
	private Integer monthPays;
	private Integer monthsPaid;
	private String details;

	//BUILDERS
	public MonthlyExpenseResponse(MonthlyExpense monthlyExpense) {
		if (monthlyExpense != null) {
			this.id = monthlyExpense.getId() != null ? monthlyExpense.getId() : null;
			this.amount = monthlyExpense.getAmount() != null ? monthlyExpense.getAmount() : null;
			this.monthPays = monthlyExpense.getMonthPays() != null ? monthlyExpense.getMonthPays() : null;
			this.monthsPaid = monthlyExpense.getMonthsPaid() != null ? monthlyExpense.getMonthsPaid() : null;
			this.details = monthlyExpense.getDetails() != null ? monthlyExpense.getDetails() : null;
		}
	}

	public MonthlyExpenseResponse() {
	}

}
