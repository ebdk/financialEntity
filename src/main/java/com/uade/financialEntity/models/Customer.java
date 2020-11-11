package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.CustomerRequest;
import com.uade.financialEntity.messages.responses.CustomerFullResponse;
import com.uade.financialEntity.messages.responses.CustomerResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Customer")
@Table(name = "customer")
@Getter
@Setter
public class Customer {

	//ATTRIBUTES
	@Id
	@Column(name = "CUSTOMER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Card> cards;

	@OneToOne(cascade = {CascadeType.ALL})
	private User user;

	private Integer dni;
	private String firstname;
	private String lastname;
	private String address;
	private Integer phone;
	private Integer salary;

	//BUILDERS
	public Customer(CustomerRequest request) {
		this.dni = request.getDni() != null ? request.getDni() : dni;
		this.firstname = request.getFirstname() != null ? request.getFirstname() : firstname;
		this.lastname = request.getLastname() != null ? request.getLastname() : lastname;
		this.address = request.getAddress() != null ? request.getAddress() : address;
		this.phone = request.getPhone() != null ? request.getPhone() : phone;
		this.salary = request.getSalary() != null ? request.getSalary() : salary;
	}

	public Customer() {
	}

	//METHODS
	public CustomerResponse toDto() {
		return new CustomerResponse(this);
	}


	public CustomerFullResponse toFullDto() {
		return new CustomerFullResponse(this);
	}

	public void modify(CustomerRequest request) {
		this.dni = request.getDni() != null ? request.getDni() : dni;
		this.firstname = request.getFirstname() != null ? request.getFirstname() : firstname;
		this.lastname = request.getLastname() != null ? request.getLastname() : lastname;
		this.address = request.getAddress() != null ? request.getAddress() : address;
		this.phone = request.getPhone() != null ? request.getPhone() : phone;
		this.salary = request.getSalary() != null ? request.getSalary() : salary;
	}
}
