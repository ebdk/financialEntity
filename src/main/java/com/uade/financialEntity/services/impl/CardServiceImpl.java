package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardFullResponse;
import com.uade.financialEntity.models.*;
import com.uade.financialEntity.repositories.*;
import com.uade.financialEntity.services.CardService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.uade.financialEntity.utils.MathUtils.getPercentage;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardDAO cardRepository;

	@Autowired
	private MonthResumeDAO monthResumeRepository;

	@Autowired
	private PurchaseDAO purchaseRepository;

	@Autowired
	private CardEntityDAO cardEntityRepository;

	@Autowired
	private CustomerDAO customerRepository;

	@Override
	public List<CardFullResponse> getAllCards() {
		List<Card> cards = cardRepository.findAll();
		return cards.stream().map(Card::toFullDto).collect(Collectors.toList());
	}

	@Override
	public Object get(Long id) {
		Optional<Card> card = cardRepository.findById(id);
		return card.isPresent() ?
				card.get().toFullDto() :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con id " + id)).getMapMessage();
	}

	@Override
	public Object getByCreditNumber(Integer creditnumber) {
		Optional<Card> card = cardRepository.findByCreditNumber(creditnumber);
		return card.isPresent() ?
				card.get().toFullDto() :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con numero " + creditnumber)).getMapMessage();
	}

	@Override
	public Object createCard(List<CardRequest> cardRequests) {
		List<Card> cards = new ArrayList<>();
		cardRequests.forEach(cardRequest -> {
			Card card = new Card(cardRequest);

			Long idCustomer = cardRequest.getCustomerId();
			Optional<Customer> optionalCustomer = customerRepository.findById(idCustomer);
			optionalCustomer.ifPresent(card::setCustomer);

			Long idCardEntity = cardRequest.getCardEntityId();
			Optional<CardEntity> optionalCardEntity = cardEntityRepository.findById(idCardEntity);
			optionalCardEntity.ifPresent(card::setCardEntity);

			MonthResume monthResume = new MonthResume(1);
			monthResume.setCard(card);

			monthResumeRepository.save(monthResume);
			cards.add(card);
		});

		cardRepository.saveAll(cards);
		return cards.stream().map(Card::toDto).collect(toList());
	}

	@Override
	public Object closeLastMonthResume(Long id) {
		Optional<Card> optionalCard = cardRepository.findById(id);

		if (optionalCard.isPresent()) {
			Card card = optionalCard.get();

			List<Purchase> monthlyPay = card.getPurchasesRemainingMonthPay();
			monthlyPay.forEach(Purchase::increasePayMonth);
			List<Purchase> cloneMonthlyPay = card.clonePurchase(monthlyPay);

			MonthResume monthResumeOpen = card.getLastMonthResumeOpen();
			monthResumeOpen.addPurchases(cloneMonthlyPay);

			Integer leftOver = 0;
			Integer amount = monthResumeOpen.calculateTotalAmount();
			if (!monthResumeOpen.paidCorrectly()) {
				Integer amountleftOver = monthResumeOpen.getAmountToPay() - monthResumeOpen.getAmountPaid();
				leftOver = amountleftOver + getPercentage(amountleftOver, 30);
			}
			monthResumeOpen.setAmountToPay(amount + leftOver);
			monthResumeOpen.close();

			MonthResume newMonthResume = new MonthResume(monthResumeOpen.getMonthNumber() + 1);

			List<MonthResume> monthResumesToSave = asList(monthResumeOpen, newMonthResume);
			monthResumeRepository.saveAll(monthResumesToSave);

			monthlyPay.addAll(cloneMonthlyPay);
			purchaseRepository.saveAll(monthlyPay);

			return monthResumeOpen.toDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con numero " + id)).getMapMessage();
		}

	}

	@Override
	public Object getOpenResumeByResumeId(Long idResume) {
		Optional<MonthResume> monthResume = monthResumeRepository.findById(idResume);
		return monthResume.isPresent() ?
				monthResume.get().toFullDto() :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada el resumen con id " + idResume)).getMapMessage();
	}

	@Override
	public Object getOpenResume(Long cardId) {
		Optional<Card> optionalCard = cardRepository.findById(cardId);
		return optionalCard.isPresent() ?
				optionalCard.get().getLastMonthResumeOpen().toFullDto() :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con id " + cardId)).getMapMessage();
	}

	@Override
	public Object getAllResumes(Long cardId) {
		Optional<Card> optionalCard = cardRepository.findById(cardId);
		return optionalCard.isPresent() ?
				optionalCard.get().getMonthResumes().stream().map(MonthResume::toFullDto) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con id " + cardId)).getMapMessage();
	}

	@Override
	public Object payForLastResume(Long cardId, Integer amount) {
		Optional<Card> optionalCard = cardRepository.findById(cardId);
		if (optionalCard.isPresent()) {
			Card card = optionalCard.get();

			MonthResume monthResume = card.getLastMonthResumeOpen();
			monthResume.setAmountPaid(monthResume.getAmountPaid() + amount);

			monthResumeRepository.save(monthResume);

			return monthResume.toFullDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con id " + cardId)).getMapMessage();
		}

	}


}