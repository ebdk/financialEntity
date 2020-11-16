package com.uade.financialEntity.messages.responses.custom;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.responses.MonthResumeFullResponse;
import com.uade.financialEntity.messages.responses.ShopPaymentResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class CloseMonthResponse implements Response {

	//ATTRIBUTES
	private List<MonthResumeFullResponse> customerMonthResume;
	private List<ShopPaymentResponse> shopPayments;

	//BUILDERS
	public CloseMonthResponse(List<MonthResumeFullResponse> customerMonthResume, List<ShopPaymentResponse> shopPayments) {
		this.customerMonthResume = customerMonthResume;
		this.shopPayments = shopPayments;
	}

	public CloseMonthResponse() {
	}
}
