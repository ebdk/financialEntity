package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.CustomerRequest;
import com.uade.financialEntity.messages.responses.CustomerResponse;
import com.uade.financialEntity.services.CustomerService;
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
@RequestMapping("financial_entity/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;

	@ApiOperation(
			value = "Looks up a customer by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The customer was found successfully", response = CustomerResponse.class),
	})
	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object get(
			@ApiParam(value = "The customer's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@ApiOperation(
			value = "Looks up a customer by Dni",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The customer was found successfully", response = CustomerResponse.class),
	})
	@GetMapping(path = "{dni}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getByDni(
			@ApiParam(value = "The customer's dni", required = true)
			@PathVariable("dni") Integer dni) {
		return ResponseEntity.ok(service.getByDni(dni));
	}


	@ApiOperation(
			value = "Looks up ALL customers from the database",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The customers were found successfully", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getAllCustomers() {
		return ResponseEntity.ok(service.getAllCustomers());
	}

	@ApiOperation(
			value = "Creates a customer",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The customer was crated successfully", response = CustomerResponse.class),
	})
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object createCustomer(@RequestBody CustomerRequest customerRequest) {
		return service.createCustomer(customerRequest);
	}

}