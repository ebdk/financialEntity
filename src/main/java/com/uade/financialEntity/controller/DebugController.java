package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.custom.FirstInsertRequest;
import com.uade.financialEntity.messages.requests.custom.SecondInsertRequest;
import com.uade.financialEntity.messages.requests.custom.ThirdInsertRequest;
import com.uade.financialEntity.messages.responses.CardEntityResponse;
import com.uade.financialEntity.services.DebugService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("financial_entity/api")
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

	@ApiOperation(
			value = "Creates Users and Card Entities",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The entities were created successfully", response = CardEntityResponse.class),
	})
	@PostMapping(path = "first_insert", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object firstInsert(@RequestBody FirstInsertRequest firstInsertRequest) {
		return service.firstInsert(firstInsertRequest);
	}

	@ApiOperation(
			value = "Creates Customers and Shops",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The entities were created successfully", response = CardEntityResponse.class),
	})
	@PostMapping(path = "second_insert", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object secondInsert(@RequestBody SecondInsertRequest secondInsertRequest) {
		return service.secondInsert(secondInsertRequest);
	}

	@ApiOperation(
			value = "Creates Cards and Shop Promotions",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The entities were created successfully", response = CardEntityResponse.class),
	})
	@PostMapping(path = "third_insert", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object thirdInsert(@RequestBody ThirdInsertRequest thirdInsertRequest) {
		return service.thirdInsert(thirdInsertRequest);
	}


	@ApiOperation(
			value = "Close Month",
			notes = "Tests connection")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Ping succesful", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(path = "close_month", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object closeMonth() {
		return ResponseEntity.ok(service.closeMonth());
	}

}
