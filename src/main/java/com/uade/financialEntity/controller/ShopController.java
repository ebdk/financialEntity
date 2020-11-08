package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.responses.ShopResponse;
import com.uade.financialEntity.services.ShopService;
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
@RequestMapping("financial_entity/api/shop")
public class ShopController {

	@Autowired
	private ShopService service;

	@ApiOperation(
			value = "Looks up a Shop by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Shop was found successfully", response = ShopResponse.class),
	})
	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getShop(
			@ApiParam(value = "The Shop's Id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@ApiOperation(
			value = "Looks up a Shop by Name",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Shop was found successfully", response = ShopResponse.class),
	})
	@GetMapping(path = "{shopname}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getShopByName(
			@ApiParam(value = "The Shop's Name", required = true)
			@PathVariable("shopname") String shopname) {
		return ResponseEntity.ok(service.getByName(shopname));
	}


	@ApiOperation(
			value = "Looks up ALL Shops from the database",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Shops were found successfully", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getAllShops() {
		return ResponseEntity.ok(service.getAllShops());
	}

	@ApiOperation(
			value = "Creates a Shop",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Shop was crated successfully", response = ShopResponse.class),
	})
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object createPersona(@RequestBody ShopRequest ShopRequest) {
		return service.createShop(ShopRequest);
	}

}