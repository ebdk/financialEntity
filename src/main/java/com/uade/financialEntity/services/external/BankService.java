package com.uade.financialEntity.services.external;

public interface BankService {

	String validateCbuExists(Long cbu);

	Object transfer(TransferRequest transferRequest);

	//CbuValidationResponse validateUserBankrupt(Long id);

}
