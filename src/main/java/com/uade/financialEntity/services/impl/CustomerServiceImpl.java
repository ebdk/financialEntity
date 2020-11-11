package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CustomerRequest;
import com.uade.financialEntity.messages.responses.CustomerResponse;
import com.uade.financialEntity.models.Customer;
import com.uade.financialEntity.models.User;
import com.uade.financialEntity.repositories.CustomerDAO;
import com.uade.financialEntity.repositories.UserDAO;
import com.uade.financialEntity.services.CustomerService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerRepository;

	@Autowired
	private UserDAO userRepository;

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
		List<Customer> customers = new ArrayList<>();
		customerRequests.forEach(cardRequest -> {
			Customer customer = new Customer(cardRequest);

			Long idUser = cardRequest.getUserId();
			Optional<User> optionalUser = userRepository.findById(idUser);
			optionalUser.ifPresent(customer::setUser);

			customers.add(customer);
		});

		customerRepository.saveAll(customers);
		return customers.stream().map(Customer::toDto).collect(toList());
	}

	@Override
	public Object delete(Long id) {
		customerRepository.deleteById(id);
		return new MessageResponse("Removed Succesfuly");
	}

	@Override
	public Object modify(Long id, CustomerRequest request) {
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			customer.modify(request);
			customerRepository.save(customer);
			return customer.toFullDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada el negocio con id " + id)).getMapMessage();
		}
	}

}