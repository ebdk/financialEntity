package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.UserRequest;
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

	private String userName;
	private String password;
	private Privilege privilege;

	public enum Privilege {
		COMMON,
		ADMIN
	}

	//BUILDERS
	public User(UserRequest userRequest) {
		this.userName = userRequest.getUserName();
		this.password = userRequest.getPassword();
		this.privilege = Privilege.valueOf(userRequest.getPrivilege());
	}

	public User() {
	}

	//METHODS
	public UserResponse toDto() {
		return new UserResponse(this);
	}

}
