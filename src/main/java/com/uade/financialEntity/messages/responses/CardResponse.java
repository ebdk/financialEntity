package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Card;
import com.uade.financialEntity.models.MonthResume;
import com.uade.financialEntity.models.MonthlyExpense;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CardResponse implements Response {

	//ATTRIBUTESo
	private Long id;
	private Long customerId;
	private List<Long> monthResumes;
	private List<Long> monthlyExpenses;
	private Long cardEntityId;
	private Integer creditNumber;
	private Integer codeNumber;
	private String validFrom;
	private String goodThrough;
	private String nameCustomer;
	private String cardType;
	private Boolean cardPayOnTime;

	//BUILDERS
	public CardResponse(Card card) {
		if (card != null) {
			this.id = card.getId() != null ? card.getId() : null;
			this.customerId = card.getCustomer() != null ? card.getCustomer().getId() : null;
			this.monthResumes = card.getMonthResumes() != null
					? card.getMonthResumes().stream().map(MonthResume::getId).collect(Collectors.toList()) : null;
			this.monthlyExpenses = card.getMonthResumes() != null
					? card.getMonthlyExpenses().stream().map(MonthlyExpense::getId).collect(Collectors.toList()) : null;
			this.cardEntityId = card.getCardEntity() != null ? card.getCardEntity().getId() : null;
			this.creditNumber = card.getCreditNumber() != null ? card.getCreditNumber() : null;
			this.codeNumber = card.getCodeNumber() != null ? card.getCodeNumber() : null;
			this.validFrom = card.getValidFrom() != null ? card.getValidFrom() : null;
			this.goodThrough = card.getGoodThrough() != null ? card.getGoodThrough() : null;
			this.nameCustomer = card.getNameCustomer() != null ? card.getNameCustomer() : null;
			this.cardType = card.getCardType() != null ? card.getCardType().toString() : null;
			this.cardPayOnTime = card.getCardPayOnTime() != null ? card.getCardPayOnTime() : null;

		}
	}

	public CardResponse() {
	}

}
