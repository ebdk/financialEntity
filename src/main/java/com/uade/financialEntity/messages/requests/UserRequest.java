package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.User;
import lombok.Getter;

@Getter
public class UserRequest implements Response {

	//ATTRIBUTES
	private String userName;
	private String password;
	private String type;

	//BUILDERS
	public User toEntity() {
		return new User(this);
	}

	public UserRequest() {
	}

}
