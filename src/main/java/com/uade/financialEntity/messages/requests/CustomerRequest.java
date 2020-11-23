package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Customer;
import lombok.Getter;

@Getter
public class CustomerRequest implements Response {

	//ATTRIBUTES
	private Long dni;
	private Long userId;
	private String firstname;
	private String lastname;
	private String address;
	private Long phone;
	private Long salary;
	private String cbuForBank;

	//BUILDERS
	public CustomerRequest(Customer customer) {
		this.dni = customer.getDni();
		this.firstname = customer.getFirstname();
		this.lastname = customer.getLastname();
		this.address = customer.getAddress();
		this.phone = customer.getPhone();
		this.salary = customer.getSalary();
	}

	public CustomerRequest() {
	}

	public Customer toEntity() {
		return new Customer(this);
	}

}
