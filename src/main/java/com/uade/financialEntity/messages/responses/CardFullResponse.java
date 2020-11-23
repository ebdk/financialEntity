package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Card;
import com.uade.financialEntity.models.MonthResume;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Getter
public class CardFullResponse implements Response {

	//ATTRIBUTESo
	private Long id;
	private CustomerResponse customer;
	Map<Integer, List<MonthResumeFullResponse>> monthResumeMap;
	private CardEntityResponse cardEntity;
	private Long creditNumber;
	private Integer secretCode;
	private String validFrom;
	private String goodThrough;
	private String nameCustomer;
	private Long amountUntilOnePayLimit;
	private Long amountUntilMonthlyPayLimit;

	//BUILDERS
	public CardFullResponse(Card card) {
		if (card != null) {
			this.id = card.getId() != null ? card.getId() : null;
			this.customer = card.getCustomer() != null ? card.getCustomer().toDto() : null;

			List<MonthResumeFullResponse> monthResumes = card.getMonthResumes() != null
					? card.getMonthResumes()
					.stream()
					.map(MonthResume::toFullDto)
					.collect(Collectors.toList()) : null;
			if (monthResumes != null) {
				this.monthResumeMap = monthResumes
						.stream()
						.collect(groupingBy(MonthResumeFullResponse::getMonthNumber));
			}


			this.cardEntity = card.getCardEntity() != null ? card.getCardEntity().toDto() : null;
			this.creditNumber = card.getCreditNumber() != null ? card.getCreditNumber() : null;
			this.secretCode = card.getSecretCode() != null ? card.getSecretCode() : null;
			this.validFrom = card.getValidFrom() != null ? card.getValidFrom() : null;
			this.goodThrough = card.getGoodThrough() != null ? card.getGoodThrough() : null;
			this.nameCustomer = card.getNameCustomer() != null ? card.getNameCustomer() : null;
			this.amountUntilOnePayLimit = card.getAmountUntilOnePayLimit() != null ? card.getAmountUntilOnePayLimit() : null;
			this.amountUntilMonthlyPayLimit = card.getAmountUntilMonthlyPayLimit() != null ? card.getAmountUntilMonthlyPayLimit() : null;
		}
	}

	public CardFullResponse() {
	}

}
