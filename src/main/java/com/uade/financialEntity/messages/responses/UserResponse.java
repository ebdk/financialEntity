package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.User;
import lombok.Getter;

@Getter
public class UserResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private String userName;
	private String password;

	//BUILDERS
	public UserResponse(User user) {
		if (user != null) {
			this.id = user.getId() != null ? user.getId() : null;
			this.userName = user.getUserName() != null ? user.getUserName() : null;
			this.password = user.getPassword() != null ? user.getPassword() : null;
		}
	}

	public UserResponse() {
	}

}
