package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.CardEntityRequest;
import com.uade.financialEntity.messages.responses.CardEntityResponse;
import com.uade.financialEntity.services.CardEntityService;
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
@RequestMapping("financial_entity/api/card_entity")
public class CardEntityController {

	@Autowired
	private CardEntityService service;

	@ApiOperation(
			value = "Looks up a cardEntity by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The cardEntity was found successfully", response = CardEntityResponse.class),
	})
	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getcardEntity(
			@ApiParam(value = "The cardEntity's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@ApiOperation(
			value = "Looks up a Card Entity by name",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Card Entity was found successfully", response = CardEntityResponse.class),
	})
	@GetMapping(path = "{name}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getcardEntityByCreditnumber(
			@ApiParam(value = "The Card Entity's name", required = true)
			@PathVariable("name") String name) {
		return ResponseEntity.ok(service.getByName(name));
	}


	@ApiOperation(
			value = "Looks up ALL Card Entities from the database",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The cardEntitys were found successfully", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getAllCardEntities() {
		return ResponseEntity.ok(service.getAllCardEntities());
	}

	@ApiOperation(
			value = "Creates a cardEntity",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The cardEntity was crated successfully", response = CardEntityResponse.class),
	})
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object createCardEntity(@RequestBody CardEntityRequest cardEntityRequest) {
		return service.createCardEntity(cardEntityRequest);
	}

}