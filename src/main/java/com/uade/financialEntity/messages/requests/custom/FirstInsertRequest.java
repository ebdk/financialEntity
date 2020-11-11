package com.uade.financialEntity.messages.requests.custom;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.CardEntityRequest;
import com.uade.financialEntity.messages.requests.CustomerRequest;
import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.requests.UserRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class FirstInsertRequest implements Response {

	//ATTRIBUTES
	//private List<CustomerRequest> customerRequests;
	private List<CardEntityRequest> cardEntityRequests;
	private List<ShopRequest> shopRequests;
	private List<UserRequest> userRequests;

	//BUILDERS
	public FirstInsertRequest() {
	}

}
