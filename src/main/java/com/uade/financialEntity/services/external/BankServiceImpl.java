package com.uade.financialEntity.services.external;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankServiceImpl implements BankService {

	@Override
	public String validateCbuExists(Long cbu) {
		return null;
	}

	@Override
	public TransferResponse transfer(Integer amount, String cbu, String detail, String providerCode) {

		TransferRequest transferRequest = new TransferRequest(amount, cbu, detail, providerCode);

		final String uri = "https://bank-api-integrations.herokuapp.com/api/v1/external/supplierpayments";

		RestTemplate restTemplate = new RestTemplate();

		try {
			return restTemplate.postForObject(uri, transferRequest ,TransferResponse.class);
		}
		catch(Exception e) {
			return null;
		}

	}

}