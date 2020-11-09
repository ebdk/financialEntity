package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.custom.FirstInsertRequest;

public interface DebugService {

	Object ping();

	Object firstInsert(FirstInsertRequest firstInsertRequest);

}
