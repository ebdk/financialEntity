package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.responses.CardResponse;
import com.uade.financialEntity.messages.responses.MonthResumeResponse;
import com.uade.financialEntity.services.CardSolicitationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("financial_entity/api/card_solicitation")
public class CardSolicitationController {

	@Autowired
	private CardSolicitationService service;

	@ApiOperation(
			value = "Looks up a card solicitation by Id",
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
			value = "Looks up card solicitations by dni",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = CardResponse.class),
	})
	@GetMapping(path = "query/{dni}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getByCustomerDni(
			@ApiParam(value = "The card solicitation's customer's dni", required = true)
			@PathVariable("dni") Long dni) {
		return ResponseEntity.ok(service.getByCustomerDni(dni));
	}


	@ApiOperation(
			value = "Looks up ALL card solicitations from the database",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The cards were found successfully", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getAllCards() {
		return ResponseEntity.ok(service.getAllCardSolicitations());
	}

	@ApiOperation(
			value = "Solicitate a Card",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = MonthResumeResponse.class),
	})
	@PostMapping(path = "solicitate/{customerId}/{cardEntityId}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object solicitate(
			@ApiParam(value = "The customer's id", required = true)
			@PathVariable("customerId") Long customerId,
			@ApiParam(value = "The cardEntity's id", required = true)
			@PathVariable("cardEntityId") Long cardEntityId
	) {
		return ResponseEntity.ok(service.solicitate(customerId, cardEntityId));
	}

	@ApiOperation(
			value = "Accept a Card Solicitation",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = MonthResumeResponse.class),
	})
	@PutMapping(path = "accept/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object accept(
			@ApiParam(value = "The card solicitation's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.accept(id));
	}

	@ApiOperation(
			value = "Accept a Card Solicitation",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = MonthResumeResponse.class),
	})
	@PutMapping(path = "decline/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object decline(
			@ApiParam(value = "The card solicitation's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.decline(id));
	}

	@ApiOperation(
			value = "Deletes a card by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was deleted successfully", response = CardResponse.class),
	})
	@DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object delete(
			@ApiParam(value = "The card's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.delete(id));
	}

}