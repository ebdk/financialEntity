package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Customer;
import lombok.Getter;

@Getter
public class CustomerRequest implements Response {

	//ATTRIBUTES
	private Integer dni;
	private String firstname;
	private String lastname;
	private String address;
	private Integer phone;
	private Integer salary;

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
