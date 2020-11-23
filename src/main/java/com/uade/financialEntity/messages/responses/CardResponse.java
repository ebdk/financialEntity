package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Card;
import com.uade.financialEntity.models.MonthResume;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CardResponse implements Response {

	//ATTRIBUTESo
	private Long id;
	private Long customerId;
	private List<Long> monthResumes;
	private Long cardEntityId;
	private Long creditNumber;
	private Integer secretCode;
	private String validFrom;
	private String goodThrough;
	private String nameCustomer;
	private Long amountUntilOnePayLimit;
	private Long amountUntilMonthlyPayLimit;

	//BUILDERS
	public CardResponse(Card card) {
		if (card != null) {
			this.id = card.getId() != null ? card.getId() : null;
			this.customerId = card.getCustomer() != null ? card.getCustomer().getId() : null;
			this.monthResumes = card.getMonthResumes() != null
					? card.getMonthResumes().stream().map(MonthResume::getId).collect(Collectors.toList()) : null;
			this.cardEntityId = card.getCardEntity() != null ? card.getCardEntity().getId() : null;
			this.creditNumber = card.getCreditNumber() != null ? card.getCreditNumber() : null;
			this.secretCode = card.getSecretCode() != null ? card.getSecretCode() : null;
			this.validFrom = card.getValidFrom() != null ? card.getValidFrom() : null;
			this.goodThrough = card.getGoodThrough() != null ? card.getGoodThrough() : null;
			this.nameCustomer = card.getNameCustomer() != null ? card.getNameCustomer() : null;
			this.amountUntilOnePayLimit = card.getAmountUntilOnePayLimit() != null ? card.getAmountUntilOnePayLimit() : null;
			this.amountUntilMonthlyPayLimit = card.getAmountUntilMonthlyPayLimit() != null ? card.getAmountUntilMonthlyPayLimit() : null;
		}
	}

	public CardResponse() {
	}

}
