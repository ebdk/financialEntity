package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.custom.PurchaseCustomRequest;
import com.uade.financialEntity.messages.responses.PurchaseResponse;

import java.util.List;

public interface PurchaseService {

	List<PurchaseResponse> getAllPurchases();

	Object get(Long name);

	Object purchase(PurchaseCustomRequest name);

	Object delete(Long id);

}
