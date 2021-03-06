package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.UserRequest;
import com.uade.financialEntity.messages.responses.CardResponse;
import com.uade.financialEntity.messages.responses.MonthResumeResponse;
import com.uade.financialEntity.messages.responses.UserResponse;
import com.uade.financialEntity.services.UserService;
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
@RequestMapping("financial_entity/api/user")
public class UserController {

	@Autowired
	private UserService service;

	@ApiOperation(
			value = "Looks up a user by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The user was found successfully", response = UserResponse.class),
	})
	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getUser(
			@ApiParam(value = "The user's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@ApiOperation(
			value = "Looks up a user by username",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The user was found successfully", response = UserResponse.class),
	})
	@GetMapping(path = "queryOne/{username}/", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getUserByUserName(
			@ApiParam(value = "The user's username", required = true)
			@PathVariable(value = "username") String username) {
		return ResponseEntity.ok(service.getByUsername(username));
	}


	@ApiOperation(
			value = "Looks up ALL users from the database",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The users were found successfully", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getAllUsers() {
		return ResponseEntity.ok(service.getAllusers());
	}


	@ApiOperation(
			value = "Validates data from User",
			notes = "Looks up the user and tries to match it's password with the one given")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The user was validated successfully", response = Object.class),
	})
	@GetMapping(path = "validate/{username}/{password}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getValidation(
			@ApiParam(value = "The user's username", required = true)
			@PathVariable("username") String username,
			@ApiParam(value = "The user's password", required = true)
			@PathVariable("password") String password) {
		return ResponseEntity.ok(service.validateByUserNameAndPassword(username, password));
	}

	@ApiOperation(
			value = "Creates Users",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The users were created successfully", response = UserResponse.class),
	})
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object createPersona(@RequestBody List<UserRequest> userRequests) {
		return service.createUsers(userRequests);
	}

	@ApiOperation(
			value = "Deletes a user by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The user was deleted successfully", response = CardResponse.class),
	})
	@DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object delete(
			@ApiParam(value = "The user's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.delete(id));
	}

	@ApiOperation(
			value = "Modifies a user",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The user was found successfully", response = MonthResumeResponse.class),
	})
	@PutMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object modify(
			@ApiParam(value = "The user's id", required = true)
			@PathVariable("id") Long id,
			@ApiParam(value = "Modifications", required = true)
			@RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(service.modify(id, userRequest));
	}

	@ApiOperation(
			value = "Looks up a customer by user Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The customer was found successfully", response = UserResponse.class),
	})
	@GetMapping(path = "customer/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getCustomer(
			@ApiParam(value = "The user's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.getCustomer(id));
	}

	@ApiOperation(
			value = "Looks up a customer by Shop Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The customer was found successfully", response = UserResponse.class),
	})
	@GetMapping(path = "shop/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getShop(
			@ApiParam(value = "The user's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.getShop(id));
	}

	@ApiOperation(
			value = "Validates if it exists a User with given username",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The customer was found successfully", response = UserResponse.class),
	})
	@GetMapping(path = "exists_username/{username}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getShop(
			@ApiParam(value = "The user's username", required = true)
			@PathVariable("username") String username) {
		return ResponseEntity.ok(service.existsUsername(username));
	}


}