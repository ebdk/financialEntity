package com.uade.financialEntity.services.external;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankServiceImpl implements BankService {

	private static String PROVIDER_CODE = "044038";

	@Override
	public boolean validateCbuExists(String cbu) {


		final String uri = "https://bank-api-integrations.herokuapp.com/api/v1/clients/search/cbu/" + cbu;

		RestTemplate restTemplate = new RestTemplate();

		try {
			CbuValidateResponse cbuValidateResponse = restTemplate.getForObject(uri, CbuValidateResponse.class);
			if (cbuValidateResponse.getMessage() != null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public TransferResponse transfer(Integer amount, String cbu, String detail) {

		TransferRequest transferRequest = new TransferRequest(amount, cbu, detail, PROVIDER_CODE);

		final String uri = "https://bank-api-integrations.herokuapp.com/api/v1/external/supplierpayments";

		RestTemplate restTemplate = new RestTemplate();

		try {
			return restTemplate.postForObject(uri, transferRequest, TransferResponse.class);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public CustomerPaymentResponse transfer(Integer amount, String cbu) {

		CustomerPaymentRequest customerPaymentRequest = new CustomerPaymentRequest(amount, cbu, PROVIDER_CODE);

		final String uri = "https://bank-api-integrations.herokuapp.com/api/v1/external/payment";

		RestTemplate restTemplate = new RestTemplate();

		try {
			return restTemplate.postForObject(uri, customerPaymentRequest, CustomerPaymentResponse.class);
		} catch (Exception e) {
			return null;
		}
	}

}