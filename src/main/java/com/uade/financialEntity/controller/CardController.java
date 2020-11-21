package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardResponse;
import com.uade.financialEntity.messages.responses.MonthResumeResponse;
import com.uade.financialEntity.messages.responses.UserResponse;
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
			@PathVariable("creditnumber") Long creditnumber) {
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

	/*
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
 */

	@ApiOperation(
			value = "Gets Resume by Resume ID",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = MonthResumeResponse.class),
	})
	@GetMapping(path = "resume_id/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getOpenResumeByResumeId(
			@ApiParam(value = "The resume's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.getOpenResumeByResumeId(id));
	}

	@ApiOperation(
			value = "Gets Open Resume by Card ID",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = MonthResumeResponse.class),
	})
	@GetMapping(path = "open_resume/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getOpenResume(
			@ApiParam(value = "The card's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.getOpenResume(id));
	}

	@ApiOperation(
			value = "Gets All Resumes of a Card by Card ID",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = MonthResumeResponse.class),
	})
	@GetMapping(path = "all_resumes/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getAllResumes(
			@ApiParam(value = "The card's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.getAllResumes(id));
	}

	@ApiOperation(
			value = "Pays a card's last open resume by Card Id and Amount to pay",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = MonthResumeResponse.class),
	})
	@PostMapping(path = "pay/{id}/{amount}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object payForLastResume(
			@ApiParam(value = "The card's id", required = true)
			@PathVariable("id") Long id,
			@ApiParam(value = "The amount to pay", required = true)
			@PathVariable("amount") Integer amount) {
		return ResponseEntity.ok(service.payForLastResume(id, amount));
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

	@ApiOperation(
			value = "Modifies a Card",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The card was found successfully", response = MonthResumeResponse.class),
	})
	@PutMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object modify(
			@ApiParam(value = "The card's id", required = true)
			@PathVariable("id") Long id,
			@ApiParam(value = "Modifications", required = true)
			@RequestBody CardRequest cardRequest) {
		return ResponseEntity.ok(service.modify(id, cardRequest));
	}

	@ApiOperation(
			value = "Deletes a Resume by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The resume was deleted successfully", response = CardResponse.class),
	})
	@DeleteMapping(path = "resume/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object deleteResume(
			@ApiParam(value = "The resume's id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.deleteResume(id));
	}

	@ApiOperation(
			value = "Validates if it exists a Card with given Credit Number",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The shop was found successfully", response = UserResponse.class),
	})
	@GetMapping(path = "exists_credit_number/{creditNumber}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object existsCreditNumber(
			@ApiParam(value = "The Card's Credit Number", required = true)
			@PathVariable("creditNumber") Long creditNumber) {
		return ResponseEntity.ok(service.existsCreditNumber(creditNumber));
	}

}