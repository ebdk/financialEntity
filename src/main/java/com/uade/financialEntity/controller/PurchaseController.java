package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.custom.PurchaseCustomRequest;
import com.uade.financialEntity.messages.responses.CustomerResponse;
import com.uade.financialEntity.messages.responses.PurchaseResponse;
import com.uade.financialEntity.services.PurchaseService;
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
@RequestMapping("financial_entity/api/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseService service;

	@ApiOperation(
			value = "Looks up a Purchase by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Purchase was found successfully", response = PurchaseResponse.class),
	})
	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getcardEntity(
			@ApiParam(value = "The Purchase's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@ApiOperation(
			value = "Looks up ALL Purchases from the database",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Purchases were found successfully", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getAllCardEntities() {
		return ResponseEntity.ok(service.getAllPurchases());
	}

	@ApiOperation(
			value = "Purchases",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Purchase was crated successfully", response = CustomerResponse.class),
	})
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object purchase(@RequestBody PurchaseCustomRequest purchaseCustomRequest) {
		return service.purchase(purchaseCustomRequest);
	}

}