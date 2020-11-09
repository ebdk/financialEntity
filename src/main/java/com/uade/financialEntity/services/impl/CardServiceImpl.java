package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardResponse;
import com.uade.financialEntity.models.Card;
import com.uade.financialEntity.models.MonthResume;
import com.uade.financialEntity.repositories.CardDAO;
import com.uade.financialEntity.services.CardService;
import com.uade.financialEntity.utils.MathUtils;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardDAO cardRepository;

	@Override
	public List<CardResponse> getAllCards() {
		List<Card> cards = cardRepository.findAll();
		return cards.stream().map(Card::toDto).collect(Collectors.toList());
	}

	@Override
	public Object get(Long id) {
		Optional<Card> card = cardRepository.findById(id);
		return card.isPresent() ?
				new CardResponse(card.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la persona con id " + id)).getMapMessage();
	}

	@Override
	public Object getByCreditNumber(Integer creditnumber) {
		Optional<Card> card = cardRepository.findByCreditNumber(creditnumber);
		return card.isPresent() ?
				new CardResponse(card.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la carta con numero " + creditnumber)).getMapMessage();
	}

	@Override
	public Object createCard(CardRequest cardRequest) {
		Card newCard = new Card(cardRequest);
		cardRepository.save(newCard);
		return newCard.toDto();
	}

	@Override
	public Object closeLastMonthResume(Long id) {
		Optional<Card> optionalCard = cardRepository.findById(id);

		if (optionalCard.isPresent()) {
			Card card = optionalCard.get();

			MonthResume monthResumeOpen = card.getLastMonthResumeOpen();

			Integer leftOver = 0;
			MonthResume monthResumeClosed = card.getLastMonthResumeClosed();
			if (!monthResumeClosed.paidCorrectly()) {
				leftOver = MathUtils.getPercentage(monthResumeClosed.getAmountPaid() - monthResumeClosed.getAmountToPay(), 30);
			}
			monthResumeOpen.setAmountToPay(monthResumeOpen.calculateTotalAmount() + card.closeMonthWithMonthlyExpenses() + leftOver);
			monthResumeOpen.close();

			return monthResumeOpen.toDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la carta con numero " + id)).getMapMessage();
		}

	}

}