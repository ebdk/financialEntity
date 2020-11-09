package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardResponse;
import com.uade.financialEntity.services.CardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("financial_entity/api/card")
public class CardController {

	@Autowired
	private CardService service;

	@ApiOperation(
			value = "Looks up a card by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = CardResponse.class),
	})
	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object get(
			@ApiParam(value = "The card's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@ApiOperation(
			value = "Looks up a card by creditnumber",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = CardResponse.class),
	})
	@GetMapping(path = "queryOne/{creditnumber}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getByCreditNumber(
			@ApiParam(value = "The card's creditnumber", required = true)
			@PathVariable("creditnumber") Integer creditnumber) {
		return ResponseEntity.ok(service.getByCreditNumber(creditnumber));
	}


	@ApiOperation(
			value = "Looks up ALL cards from the database",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The cards were found successfully", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getAllCards() {
		return ResponseEntity.ok(service.getAllCards());
	}

	@ApiOperation(
			value = "Creates cards",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The cards were created successfully", response = CardResponse.class),
	})
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object createCard(@RequestBody List<CardRequest> cardRequests) {
		return service.createCard(cardRequests);
	}


	@ApiOperation(
			value = "Closes a card by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = CardResponse.class),
	})
	@PostMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object closeLastMonthResume(
			@ApiParam(value = "The card's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.closeLastMonthResume(id));
	}

}