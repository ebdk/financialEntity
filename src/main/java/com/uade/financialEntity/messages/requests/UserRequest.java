package com.uade.financialEntity.messages.requests;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.User;
import lombok.Getter;

@Getter
public class UserRequest implements Response {

	//ATTRIBUTES
	private String userName;
	private String password;

	//BUILDERS
	public UserRequest(User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
	}

	public UserRequest() {
	}

}
