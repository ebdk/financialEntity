package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.requests.custom.PurchaseCustomRequest;
import com.uade.financialEntity.messages.responses.CustomerResponse;
import com.uade.financialEntity.services.PurchaseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("financial_entity/api/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseService service;

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