package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.MonthResume;
import lombok.Getter;

@Getter
public class MonthResumeRequest implements Response {

	//ATTRIBUTES
	private Integer monthNumber;
	private Integer amountToPay;
	private Boolean open;
	private Integer amountPaid;

	//BUILDERS
	public MonthResumeRequest(MonthResume monthResume) {
		this.monthNumber = monthResume.getMonthNumber();
		this.amountToPay = monthResume.getAmountToPay();
		this.open = monthResume.getOpen();
		this.amountPaid = monthResume.getAmountPaid();
	}

	public MonthResumeRequest() {
	}

}
