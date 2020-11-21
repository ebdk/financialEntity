package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.CustomerRequest;
import com.uade.financialEntity.messages.responses.CardResponse;
import com.uade.financialEntity.messages.responses.CustomerResponse;
import com.uade.financialEntity.messages.responses.MonthResumeResponse;
import com.uade.financialEntity.messages.responses.UserResponse;
import com.uade.financialEntity.services.CustomerService;
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
	@GetMapping(path = "queryOne/{dni}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getByDni(
			@ApiParam(value = "The customer's dni", required = true)
			@PathVariable("dni") Long dni) {
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
			value = "Creates Customers",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The customers were created successfully", response = CustomerResponse.class),
	})
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object createCustomer(@RequestBody List<CustomerRequest> customerRequests) {
		return service.createCustomers(customerRequests);
	}

	@ApiOperation(
			value = "Deletes a Customer by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The customer was deleted successfully", response = CardResponse.class),
	})
	@DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object delete(
			@ApiParam(value = "The customer's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.delete(id));
	}

	@ApiOperation(
			value = "Modifies a Customer",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Customer was found successfully", response = MonthResumeResponse.class),
	})
	@PutMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object modify(
			@ApiParam(value = "The Customer's id", required = true)
			@PathVariable("id") Long id,
			@ApiParam(value = "Modifications", required = true)
			@RequestBody CustomerRequest request) {
		return ResponseEntity.ok(service.modify(id, request));
	}

	@ApiOperation(
			value = "Validates if it exists a Customer with given dni",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The shop was found successfully", response = UserResponse.class),
	})
	@GetMapping(path = "exists_dni/{dni}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object existsDni(
			@ApiParam(value = "The Customer's dni", required = true)
			@PathVariable("dni") Long dni) {
		return ResponseEntity.ok(service.existsDni(dni));
	}

}