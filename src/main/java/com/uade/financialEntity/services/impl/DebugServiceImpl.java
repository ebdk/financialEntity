package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.services.DebugService;
import org.springframework.stereotype.Service;

@Service
public class DebugServiceImpl implements DebugService {

	@Override
	public Object ping() {
		return new MessageResponse("Pong.").getMapMessage();
	}
}
