package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CustomerRequest;
import com.uade.financialEntity.messages.requests.ShopPromotionRequest;
import com.uade.financialEntity.messages.responses.CustomerResponse;
import com.uade.financialEntity.models.Customer;
import com.uade.financialEntity.models.ShopPromotion;
import com.uade.financialEntity.repositories.CustomerDAO;
import com.uade.financialEntity.services.CustomerService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerRepository;

	@Override
	public List<CustomerResponse> getAllCustomers() {
		List<Customer> personas = customerRepository.findAll();
		return personas.stream().map(Customer::toDto).collect(Collectors.toList());
	}

	@Override
	public Object get(Long id) {
		Optional<Customer> persona = customerRepository.findById(id);
		return persona.isPresent() ?
				new CustomerResponse(persona.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la persona con id " + id)).getMapMessage();
	}

	@Override
	public Object getByDni(Integer dni) {
		Optional<Customer> persona = customerRepository.findByDni(dni);
		return persona.isPresent() ?
				new CustomerResponse(persona.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la persona con dni " + dni)).getMapMessage();
	}

	@Override
	public Object createCustomers(List<CustomerRequest> customerRequests) {
		List<Customer> customers = customerRequests
				.stream()
				.map(CustomerRequest::toEntity)
				.collect(toList());

		customerRepository.saveAll(customers);
		return customers.stream().map(Customer::toDto).collect(toList());
	}

}