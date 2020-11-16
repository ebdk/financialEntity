package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.custom.FirstInsertRequest;
import com.uade.financialEntity.messages.requests.custom.SecondInsertRequest;
import com.uade.financialEntity.messages.requests.custom.ThirdInsertRequest;

public interface DebugService {

	Object ping();

	Object firstInsert(FirstInsertRequest firstInsertRequest);

	Object secondInsert(SecondInsertRequest firstInsertRequest);

	Object thirdInsert(ThirdInsertRequest thirdInsertRequest);

	Object closeMonth();

}
