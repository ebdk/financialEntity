package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Card;
import com.uade.financialEntity.models.Customer;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CustomerResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private List<Long> cards;
	private Long dni;
	private String firstname;
	private String lastname;
	private String address;
	private Long phone;
	private Long salary;
	private Long cbu;

	//BUILDERS
	public CustomerResponse(Customer customer) {
		if (customer != null) {
			this.id = customer.getId() != null ? customer.getId() : null;
			this.cards = customer.getCards() != null
					? customer.getCards().stream().map(Card::getId).collect(Collectors.toList()) : null;
			this.dni = customer.getDni() != null ? customer.getDni() : null;
			this.firstname = customer.getFirstname() != null ? customer.getFirstname() : null;
			this.lastname = customer.getLastname() != null ? customer.getLastname() : null;
			this.address = customer.getAddress() != null ? customer.getAddress() : null;
			this.phone = customer.getPhone() != null ? customer.getPhone() : null;
			this.salary = customer.getSalary() != null ? customer.getSalary() : null;
			this.cbu = customer.getCbu() != null ? customer.getCbu() : null;
		}
	}

	public CustomerResponse() {
	}

}
