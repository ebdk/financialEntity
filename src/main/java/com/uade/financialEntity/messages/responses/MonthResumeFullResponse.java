package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.MonthResume;
import com.uade.financialEntity.models.Purchase;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Getter
public class MonthResumeFullResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private Long cardId;
	Map<String, List<PurchaseFullResponse>> purchaseMap;
	private Integer monthNumber;
	private Integer amountToPay;
	private Boolean open;
	private Integer amountPaid;

	//BUILDERS
	public MonthResumeFullResponse(MonthResume monthResume) {
		if (monthResume != null) {
			this.id = monthResume.getId() != null ? monthResume.getId() : null;
			this.cardId = monthResume.getCard() != null ? monthResume.getId() : null;

			List<PurchaseFullResponse> purchases = monthResume.getPurchases() != null
					? monthResume.getPurchases()
					.stream()
					.map(Purchase::toFullDto)
					.collect(Collectors.toList()) : null;
			if (purchases != null) {
				this.purchaseMap = purchases
						.stream()
						.collect(groupingBy(PurchaseFullResponse::getPurchaseType));
			}

			this.monthNumber = monthResume.getMonthNumber() != null ? monthResume.getMonthNumber() : null;
			this.amountToPay = monthResume.getAmountToPay() != null ? monthResume.getAmountToPay() : null;
			this.open = monthResume.getOpen() != null ? monthResume.getOpen() : null;
			this.amountPaid = monthResume.getAmountPaid() != null ? monthResume.getAmountPaid() : null;
		}
	}

	public MonthResumeFullResponse() {
	}

}
