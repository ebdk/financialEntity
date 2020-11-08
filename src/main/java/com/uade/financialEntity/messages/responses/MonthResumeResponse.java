package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.MonthResume;
import lombok.Getter;

@Getter
public class MonthResumeResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private Integer monthNumber;
	private Integer amountToPay;
	private Boolean closed;
	private Integer amountPaid;

	//BUILDERS
	public MonthResumeResponse(MonthResume monthResume) {
		if (monthResume != null) {
			this.id = monthResume.getId() != null ? monthResume.getId() : null;
			this.monthNumber = monthResume.getMonthNumber() != null ? monthResume.getMonthNumber() : null;
			this.amountToPay = monthResume.getAmountToPay() != null ? monthResume.getAmountToPay() : null;
			this.closed = monthResume.getClosed() != null ? monthResume.getClosed() : null;
			this.amountPaid = monthResume.getAmountPaid() != null ? monthResume.getAmountPaid() : null;
		}
	}

	public MonthResumeResponse() {
	}

}
