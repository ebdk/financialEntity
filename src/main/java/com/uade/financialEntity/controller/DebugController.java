package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.services.DebugService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("financial_game/api")
public class DebugController {

	@Autowired
	private DebugService service;

	@ApiOperation(
			value = "Ping",
			notes = "Tests connection")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Ping succesful", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(path = "ping", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object ping() {
		return ResponseEntity.ok(service.ping());
	}

}
