package com.uade.financialEntity.services.external;

public interface BankService {

	boolean validateCbuExists(String cbu);

	TransferResponse transfer(Integer amount, String cbu, String detail);

	CustomerPaymentResponse transfer(Integer amount, String cbu);

}
