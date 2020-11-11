package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.responses.ShopResponse;

import java.util.List;

public interface ShopService {

	List<ShopResponse> getAllShops();

	Object get(Long name);

	Object getByName(String name);

	Object createShops(List<ShopRequest> shopRequests);

	Object delete(Long id);

	Object modify(Long id, ShopRequest request);

}
