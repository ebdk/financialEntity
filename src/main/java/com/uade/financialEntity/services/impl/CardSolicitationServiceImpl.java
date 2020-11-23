package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.responses.CardSolicitationResponse;
import com.uade.financialEntity.models.*;
import com.uade.financialEntity.repositories.*;
import com.uade.financialEntity.services.CardSolicitationService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.uade.financialEntity.utils.MathUtils.generateRandomNumber;
import static java.util.stream.Collectors.toList;

@Service
public class CardSolicitationServiceImpl implements CardSolicitationService {

	@Autowired
	private CardSolicitationDAO cardSolicitationRepository;

	/*
	@Autowired
	private BankService bankService;
	 */

	@Autowired
	private CustomerDAO customerRepository;

	@Autowired
	private CardEntityDAO cardEntityRepository;


	@Autowired
	private CardDAO cardRepository;

	@Autowired
	private SystemCacheDAO systemCacheRepository;

	@Autowired
	private MonthResumeDAO monthResumeRepository;

	@Override
	public List<CardSolicitationResponse> getAllCardSolicitations() {
		List<CardSolicitation> cardSolicitations = cardSolicitationRepository.findAll()
				.stream().filter(CardSolicitation::getOpen)
				.collect(toList());
		return cardSolicitations.stream().map(CardSolicitation::toDto).collect(Collectors.toList());
	}

	@Override
	public Object get(Long id) {
		Optional<CardSolicitation> cardSolicitation = cardSolicitationRepository.findById(id);
		return cardSolicitation.isPresent() ?
				new CardSolicitationResponse(cardSolicitation.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la solicitud de Tarjeta con  id " + id)).getMapMessage();
	}

	@Override
	public Object getByCustomerDni(Long dni) {
		List<CardSolicitation> cardSolicitations = cardSolicitationRepository.findAll()
				.stream().filter(cardSolicitation -> cardSolicitation.sameDni(dni) && cardSolicitation.getOpen())
				.collect(toList());
		return !cardSolicitations.isEmpty() ?
				cardSolicitations.stream().map(CardSolicitation::toDto).collect(Collectors.toList()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la solicitud de Tarjeta con dni " + dni)).getMapMessage();
	}

	@Override
	public Object solicitate(Long customerId, Long cardEntityId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		Optional<CardEntity> optionalCardEntity = cardEntityRepository.findById(cardEntityId);

		if (optionalCustomer.isPresent() && optionalCardEntity.isPresent()) {
			Customer customer = optionalCustomer.get();
			CardEntity cardEntity = optionalCardEntity.get();

			CardSolicitation cardSolicitation = new CardSolicitation(customer, cardEntity);
			String cbu = customer.getCbuForBank();

			//TODO CHECK IF CBU EXITS IN BANK AND IF IT'S NOT BANKRUPT

			/*
			CbuValidationResponse bankCall = bankService.validateUserBankrupt(cbu);
			if (!bankCall.getFailed()) {
				cardSolicitation.setConfirmedBankCheck(true);
				cardSolicitation.setConfirmedBankruptedByBank(bankCall.getIsBankrupt());
			} else {
				cardSolicitation.setConfirmedBankCheck(false);
			}
			 */

			cardSolicitationRepository.save(cardSolicitation);
			return cardSolicitation.toDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrado el cliente o entidad de tarjeta.")).getMapMessage();
		}
	}

	@Override
	public Object accept(Long id) {
		SystemCache systemCache = systemCacheRepository.findAll().get(0);
		Integer monthNumber = systemCache.getMonthNumber();

		Optional<CardSolicitation> optionalCardSolicitation = cardSolicitationRepository.findById(id);
		if (optionalCardSolicitation.isPresent() && optionalCardSolicitation.get().getOpen()) {
			CardSolicitation cardSolicitation = optionalCardSolicitation.get();

			cardSolicitation.setAccepted(true);
			cardSolicitation.setOpen(false);
			Date now = new Date();
			cardSolicitation.setDateFinalized(now);

			Card card = new Card();
			card.setCardEntity(cardSolicitation.getCardEntity());
			card.setCustomer(cardSolicitation.getCustomer());
			card.setSecretCode(generateRandomNumber(100, 999));
			card.setCreditNumber(generateRandomNumber(10000000, 99999999).longValue());
			card.setValidFrom("NOVEMBER 2020");
			card.setGoodThrough("NOVEMBER 2022");
			card.setNameCustomer(card.getCustomer().getFirstname() + " " + card.getCustomer().getLastname());

			card.setAmountUntilMonthlyPayLimit(cardSolicitation.getCardEntity().getLimitMonthlyPay());
			card.setAmountUntilOnePayLimit(cardSolicitation.getCardEntity().getLimitPay());

			MonthResume monthResume = new MonthResume(monthNumber);
			monthResume.setCard(card);

			cardRepository.save(card);
			monthResumeRepository.save(monthResume);
			cardSolicitationRepository.save(cardSolicitation);
		}
		return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la solicitud de Tarjeta con  id " + id + " o esta cerrada.")).getMapMessage();
	}

	@Override
	public Object decline(Long id) {
		Optional<CardSolicitation> optionalCardSolicitation = cardSolicitationRepository.findById(id);
		if (optionalCardSolicitation.isPresent() && optionalCardSolicitation.get().getOpen()) {
			CardSolicitation cardSolicitation = optionalCardSolicitation.get();

			cardSolicitation.setAccepted(false);
			cardSolicitation.setOpen(false);
			Date now = new Date();
			cardSolicitation.setDateFinalized(now);

			cardSolicitationRepository.save(cardSolicitation);
		}
		return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la solicitud de Tarjeta con  id " + id + " o esta cerrada.")).getMapMessage();
	}

	@Override
	public Object delete(Long id) {
		cardEntityRepository.deleteById(id);
		return new MessageResponse("Removed Succesfuly").getMapMessage();
	}

}