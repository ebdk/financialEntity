package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.responses.ShopFullResponse;
import com.uade.financialEntity.messages.responses.ShopPaymentResponse;
import com.uade.financialEntity.messages.responses.ShopResponse;
import com.uade.financialEntity.models.ShopPayment;

import java.util.List;

public interface ShopService {

	List<ShopFullResponse> getAllShops();

	Object get(Long name);

	Object getByName(String name);

	Object createShops(List<ShopRequest> shopRequests);

	Object delete(Long id);

	Object modify(Long id, ShopRequest request);

	Object existsName(String name);

	//Object closeMonth(Long id);

	//Object closeMonths();

	List<ShopPaymentResponse> closeMonths(Integer month);

}
