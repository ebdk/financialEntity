package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.User;
import lombok.Getter;

@Getter
public class UserFullResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private CustomerFullResponse customer;
	private ShopResponse shopResponse;
	private String userName;
	private String password;
	private String privilege;

	//BUILDERS
	public UserFullResponse(User user) {
		if (user != null) {
			this.id = user.getId() != null ? user.getId() : null;
			this.customer = user.getCustomer() != null ? user.getCustomer().toFullDto() : null;
			this.shopResponse = user.getShop() != null ? user.getShop().toDto() : null;
			this.userName = user.getUserName() != null ? user.getUserName() : null;
			this.password = user.getPassword() != null ? user.getPassword() : null;
			this.privilege = user.getPrivilege() != null ? user.getPrivilege().toString() : null;
		}
	}

	public UserFullResponse() {
	}

}
