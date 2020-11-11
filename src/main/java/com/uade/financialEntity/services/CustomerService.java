package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.CustomerRequest;
import com.uade.financialEntity.messages.responses.CustomerResponse;

import java.util.List;

public interface CustomerService {

	List<CustomerResponse> getAllCustomers();

	Object get(Long id);

	Object getByDni(Integer dni);

	Object createCustomers(List<CustomerRequest> customerRequests);

	Object delete(Long id);

	Object modify(Long id, CustomerRequest request);

}
