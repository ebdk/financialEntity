package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Customer;
import lombok.Getter;

@Getter
public class CustomerResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private Integer dni;
	private String firstname;
	private String lastname;
	private String address;
	private Integer phone;
	private Integer salary;

	//BUILDERS
	public CustomerResponse(Customer customer) {
		if (customer != null) {
			this.id = customer.getId() != null ? customer.getId() : null;
			this.dni = customer.getDni() != null ? customer.getDni() : null;
			this.firstname = customer.getFirstname() != null ? customer.getFirstname() : null;
			this.lastname = customer.getLastname() != null ? customer.getLastname() : null;
			this.address = customer.getAddress() != null ? customer.getAddress() : null;
			this.phone = customer.getPhone() != null ? customer.getPhone() : null;
			this.salary = customer.getSalary() != null ? customer.getSalary() : null;
		}
	}

	public CustomerResponse() {
	}

}
