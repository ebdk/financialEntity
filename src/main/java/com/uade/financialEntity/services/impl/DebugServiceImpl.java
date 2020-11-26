package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CardEntityRequest;
import com.uade.financialEntity.messages.requests.UserRequest;
import com.uade.financialEntity.messages.requests.custom.FirstInsertRequest;
import com.uade.financialEntity.messages.requests.custom.SecondInsertRequest;
import com.uade.financialEntity.messages.requests.custom.ThirdInsertRequest;
import com.uade.financialEntity.messages.responses.MonthResumeFullResponse;
import com.uade.financialEntity.messages.responses.ShopPaymentResponse;
import com.uade.financialEntity.messages.responses.custom.CloseMonthResponse;
import com.uade.financialEntity.models.*;
import com.uade.financialEntity.repositories.*;
import com.uade.financialEntity.services.CardService;
import com.uade.financialEntity.services.DebugService;
import com.uade.financialEntity.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class DebugServiceImpl implements DebugService {


	@Autowired
	private CardEntityDAO cardEntityRepository;

	@Autowired
	private CustomerDAO customerRepository;

	@Autowired
	private ShopDAO shopRepository;

	@Autowired
	private UserDAO userRepository;

	@Autowired
	private CardDAO cardRepository;

	@Autowired
	private ShopPromotionDAO shopPromotionRepository;

	@Autowired
	private MonthResumeDAO monthResumeRepository;

	@Autowired
	private SystemCacheDAO systemCacheRepository;

	@Autowired
	private ShopService shopService;

	@Autowired
	private CardService cardService;

	@Override
	public Object ping() {
		return new MessageResponse("Pong").getMapMessage();
	}

	@Override
	public Object firstInsert(FirstInsertRequest firstInsertRequest) {

		SystemCache systemCache = new SystemCache();
		systemCacheRepository.save(systemCache);

		if (firstInsertRequest.getCardEntityRequests() != null) {
			List<CardEntity> cardEntities = firstInsertRequest.getCardEntityRequests()
					.stream()
					.map(CardEntityRequest::toEntity)
					.collect(toList());

			cardEntityRepository.saveAll(cardEntities);
		}

		if (firstInsertRequest.getUserRequests() != null) {
			List<User> users = firstInsertRequest.getUserRequests()
					.stream()
					.map(UserRequest::toEntity)
					.collect(toList());

			userRepository.saveAll(users);
		}

		return new MessageResponse("Added succesfuly").getMapMessage();
	}


	@Override
	public Object secondInsert(SecondInsertRequest secondInsertRequest) {

		List<Shop> shops = new ArrayList<>();
		secondInsertRequest.getShopRequests().forEach(shopRequest -> {
			Shop customer = new Shop(shopRequest);

			Long idUser = shopRequest.getUserId();
			Optional<User> optionalUser = userRepository.findById(idUser);
			optionalUser.ifPresent(customer::setUser);

			shops.add(customer);
		});
		shopRepository.saveAll(shops);

		List<Customer> customers = new ArrayList<>();
		secondInsertRequest.getCustomerRequests().forEach(cardRequest -> {
			Customer customer = new Customer(cardRequest);

			Long idUser = cardRequest.getUserId();
			Optional<User> optionalUser = userRepository.findById(idUser);
			optionalUser.ifPresent(customer::setUser);

			customers.add(customer);
		});
		customerRepository.saveAll(customers);


		return new MessageResponse("Added succesfuly").getMapMessage();
	}

	@Override
	public Object thirdInsert(ThirdInsertRequest thirdInsertRequest) {
		/*
		thirdInsertRequest.getCardRequests().forEach(cardRequest -> {
			Card card = new Card(cardRequest);

			Long idCardEntity = cardRequest.getCardEntityId();
			Optional<CardEntity> optionalCardEntity = cardEntityRepository.findById(idCardEntity);
			optionalCardEntity.ifPresent(card::setCardEntity);

			Long idCustomer = cardRequest.getCustomerId();
			Optional<Customer> optionalCustomer = customerRepository.findById(idCustomer);
			optionalCustomer.ifPresent(card::setCustomer);

			MonthResume monthResume = new MonthResume(monthNumber);
			monthResume.setCard(card);

			cardRepository.save(card);
			monthResumeRepository.save(monthResume);
		});
		 */

		List<ShopPromotion> shopPromotions = new ArrayList<>();
		thirdInsertRequest.getShopPromotionRequests().forEach(shopPromotionRequest -> {
			ShopPromotion shopPromotion = new ShopPromotion(shopPromotionRequest);

			Long idCardEntity = shopPromotionRequest.getCardEntityId();
			Optional<CardEntity> optionalCardEntity = cardEntityRepository.findById(idCardEntity);
			optionalCardEntity.ifPresent(shopPromotion::setCardEntity);

			Long idShop = shopPromotionRequest.getShopId();
			Optional<Shop> optionalShop = shopRepository.findById(idShop);
			optionalShop.ifPresent(shopPromotion::setShop);

			shopPromotions.add(shopPromotion);
		});
		shopPromotionRepository.saveAll(shopPromotions);


		return new MessageResponse("Added succesfuly").getMapMessage();
	}

	@Override
	public Object closeMonth() {
		SystemCache systemCache = systemCacheRepository.findAll().get(0);
		Integer monthNumber = systemCache.getMonthNumber();

		List<MonthResumeFullResponse> monthResumeFullResponses = cardService.closeLastMonthResumes(monthNumber);
		List<ShopPaymentResponse> shopPaymentResponses = shopService.closeMonths(monthNumber);


		CloseMonthResponse closeMonthResponse = new CloseMonthResponse(monthResumeFullResponses, shopPaymentResponses);

		systemCache.setMonthNumber(monthNumber + 1);
		systemCacheRepository.save(systemCache);
		return closeMonthResponse;
	}

}
