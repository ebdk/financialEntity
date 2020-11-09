package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.MonthResume;
import com.uade.financialEntity.models.Purchase;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MonthResumeFullResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private CardResponse card;
	private List<PurchaseFullResponse> purchases;
	private Integer monthNumber;
	private Integer amountToPay;
	private Boolean open;
	private Integer amountPaid;

	//BUILDERS
	public MonthResumeFullResponse(MonthResume monthResume) {
		if (monthResume != null) {
			this.id = monthResume.getId() != null ? monthResume.getId() : null;
			this.card = monthResume.getCard() != null ? monthResume.getCard().toDto() : null;
			this.purchases = monthResume.getPurchases() != null
					? monthResume.getPurchases().stream().map(Purchase::toFullDto).collect(Collectors.toList()) : null;
			this.monthNumber = monthResume.getMonthNumber() != null ? monthResume.getMonthNumber() : null;
			this.amountToPay = monthResume.getAmountToPay() != null ? monthResume.getAmountToPay() : null;
			this.open = monthResume.getOpen() != null ? monthResume.getOpen() : null;
			this.amountPaid = monthResume.getAmountPaid() != null ? monthResume.getAmountPaid() : null;
		}
	}

	public MonthResumeFullResponse() {
	}

}
