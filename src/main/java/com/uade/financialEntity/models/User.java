package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.UserRequest;
import com.uade.financialEntity.messages.responses.UserFullResponse;
import com.uade.financialEntity.messages.responses.UserResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "user")
@Getter
@Setter
public class User {

	//ATTRIBUTES
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Customer customer;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Shop shop;

	private String userName;
	private String password;
	private Type type;

	public User(String username) {
		this.userName = username;
	}

	public enum Type {
		SHOP,
		CUSTOMER,
		ADMIN
	}

	//BUILDERS
	public User(UserRequest request) {
		this.userName = request.getUserName() != null ? request.getUserName() : userName;
		this.password = request.getPassword() != null ? request.getPassword() : password;
		this.type = request.getType() != null ? Type.valueOf(request.getType()) : type;
	}

	public User() {
	}

	//METHODS
	public UserResponse toDto() {
		return new UserResponse(this);
	}

	public UserFullResponse toFullDto() {
		return new UserFullResponse(this);
	}

	public void modify(UserRequest request) {
		this.userName = request.getUserName() != null ? request.getUserName() : userName;
		this.password = request.getPassword() != null ? request.getPassword() : password;
		this.type = request.getType() != null ? Type.valueOf(request.getType()) : type;
	}

}
