package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardFullResponse;
import com.uade.financialEntity.models.Card;
import com.uade.financialEntity.models.MonthResume;
import com.uade.financialEntity.models.Purchase;
import com.uade.financialEntity.repositories.CardDAO;
import com.uade.financialEntity.repositories.MonthResumeDAO;
import com.uade.financialEntity.repositories.PurchaseDAO;
import com.uade.financialEntity.services.CardService;
import com.uade.financialEntity.services.external.BankService;
import com.uade.financialEntity.utils.Pair;
import com.uade.financialEntity.utils.PairObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.uade.financialEntity.models.Purchase.PurchaseType.ONE_PAY;
import static com.uade.financialEntity.utils.MathUtils.getPercentage;
import static java.util.Arrays.asList;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardDAO cardRepository;

	@Autowired
	private MonthResumeDAO monthResumeRepository;

	@Autowired
	private PurchaseDAO purchaseRepository;

	@Autowired
	private BankService bankService;

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
	public Object getByCreditNumber(Long creditnumber) {
		Optional<Card> card = cardRepository.findByCreditNumber(creditnumber);
		return card.isPresent() ?
				card.get().toFullDto() :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con numero " + creditnumber)).getMapMessage();
	}

	@Override
	public void closeLastMonthResumes(Integer month) {

		cardRepository.findAll().forEach(card -> closeCard(card, month));

	}

	private void closeCard(Card card, Integer monthNumner) {
		List<Purchase> monthlyPay = card.getPurchasesRemainingMonthPay();
		List<Purchase> cloneMonthlyPay = card.clonePurchase(monthlyPay);

		MonthResume monthResumeOpen = card.getLastMonthResumeOpen();

		Integer leftOver = 0;
		if (monthResumeOpen.paidsArentZero()) {
			Integer amountleftOver = monthResumeOpen.getAmountToPay() - monthResumeOpen.getAmountPaid();

			if (monthResumeOpen.debts()) {
				leftOver = amountleftOver + getPercentage(amountleftOver, 30);
			} else {
				leftOver = amountleftOver;
			}
		}
		monthResumeOpen.close();

		MonthResume newMonthResume = new MonthResume(monthNumner + 1);
		newMonthResume.setCard(card);
		cloneMonthlyPay.forEach(cloneMonth -> cloneMonth.setMonthResume(newMonthResume));

		if (!leftOver.equals(0)) {
			Purchase purchase = new Purchase(ONE_PAY);
			purchase.setTotalAmount(leftOver);
			purchase.setDescription("Restante del mes " + monthNumner);
			purchase.setMonthResume(newMonthResume);
			newMonthResume.addPurchase(purchase);

			newMonthResume.addPurchases(cloneMonthlyPay);
			Integer amountToPay = newMonthResume.calculateTotalAmount();

			List<Purchase> emptyList = new ArrayList<>();
			newMonthResume.setPurchases(emptyList);

			newMonthResume.setAmountToPay(amountToPay);

			monthlyPay.add(purchase);
		}

		List<MonthResume> monthResumesToSave = asList(monthResumeOpen, newMonthResume);
		monthResumeRepository.saveAll(monthResumesToSave);

		monthlyPay.addAll(cloneMonthlyPay);
		purchaseRepository.saveAll(monthlyPay);

		//return monthResumeOpen;
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

			if (bankService.validateCbuExists(card.getCustomer().getCbuForBank())) {
				bankService.transfer(amount, card.getCustomer().getCbuForBank());
				System.out.println("SENT " + amount);

				monthResumeRepository.save(monthResume);
			} else {
				return new MessageResponse(new Pair("error", "Error, cbu invalido para el usuario: " + card.getCustomer().getCbuForBank())).getMapMessage();
			}


			return monthResume.toFullDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con id " + cardId)).getMapMessage();
		}

	}

	@Override
	public Object delete(Long cardId) {
		cardRepository.deleteById(cardId);
		return new MessageResponse("Removed Succesfuly").getMapMessage();
	}

	@Override
	public Object modify(Long id, CardRequest request) {
		Optional<Card> optionalCard = cardRepository.findById(id);
		if (optionalCard.isPresent()) {
			Card card = optionalCard.get();
			card.modify(request);
			cardRepository.save(card);
			return card.toFullDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con id " + id)).getMapMessage();
		}
	}

	@Override
	public Object deleteResume(Long id) {
		monthResumeRepository.deleteById(id);
		return new MessageResponse("Removed Succesfuly").getMapMessage();
	}

	@Override
	public Object existsCreditNumber(Long creditNumber) {
		Card card = new Card(creditNumber);
		boolean exists = cardRepository.exists(Example.of(card));
		return new MessageResponse(new PairObject("exists", exists)).getMapObject();
	}

}