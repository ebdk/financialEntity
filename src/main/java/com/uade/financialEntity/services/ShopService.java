package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.requests.UserRequest;
import com.uade.financialEntity.messages.responses.ShopResponse;
import com.uade.financialEntity.messages.responses.UserResponse;

import java.util.List;

public interface ShopService {

	List<ShopResponse> getAllShops();

	Object get(Long name);

	Object getByName(String name);

	Object createShops(List<ShopRequest> shopRequests);

}
