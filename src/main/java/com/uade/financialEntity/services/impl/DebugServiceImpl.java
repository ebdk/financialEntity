package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CardEntityRequest;
import com.uade.financialEntity.messages.requests.CustomerRequest;
import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.requests.UserRequest;
import com.uade.financialEntity.messages.requests.custom.FirstInsertRequest;
import com.uade.financialEntity.models.CardEntity;
import com.uade.financialEntity.models.Customer;
import com.uade.financialEntity.models.Shop;
import com.uade.financialEntity.models.User;
import com.uade.financialEntity.repositories.CardEntityDAO;
import com.uade.financialEntity.repositories.CustomerDAO;
import com.uade.financialEntity.repositories.ShopDAO;
import com.uade.financialEntity.repositories.UserDAO;
import com.uade.financialEntity.services.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	@Override
	public Object ping() {
		return new MessageResponse("Pong").getMapMessage();
	}

	@Override
	public Object firstInsert(FirstInsertRequest firstInsertRequest) {

		if (firstInsertRequest.getShopRequests() != null) {
			List<Shop> shops = firstInsertRequest.getShopRequests()
					.stream()
					.map(ShopRequest::toEntity)
					.collect(toList());

			shopRepository.saveAll(shops);
		}

		if (firstInsertRequest.getCardEntityRequests() != null) {
			List<CardEntity> cardEntities = firstInsertRequest.getCardEntityRequests()
					.stream()
					.map(CardEntityRequest::toEntity)
					.collect(toList());

			cardEntityRepository.saveAll(cardEntities);
		}

		if (firstInsertRequest.getCustomerRequests() != null) {
			List<Customer> customers = firstInsertRequest.getCustomerRequests()
					.stream()
					.map(CustomerRequest::toEntity)
					.collect(toList());

			customerRepository.saveAll(customers);
		}

		if (firstInsertRequest.getUserRequests() != null) {
			List<User> users = firstInsertRequest.getUserRequests()
					.stream()
					.map(UserRequest::toEntity)
					.collect(toList());

			userRepository.saveAll(users);
		}

		return new MessageResponse("Added succesfuly");
	}

}
