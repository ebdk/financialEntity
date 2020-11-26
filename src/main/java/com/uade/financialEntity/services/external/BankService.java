package com.uade.financialEntity.services.external;

public interface BankService {

	String validateCbuExists(Long cbu);

	TransferResponse transfer(Integer amount, String cbu, String detail, String providerCode);

}
