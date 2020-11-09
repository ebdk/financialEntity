package com.uade.financialEntity.controller;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.messages.requests.ShopPromotionRequest;
import com.uade.financialEntity.messages.responses.ShopResponse;
import com.uade.financialEntity.services.ShopPromotionService;
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
@RequestMapping("financial_entity/api/shop_promotion")
public class ShopPromotionController {

	@Autowired
	private ShopPromotionService service;

	@ApiOperation(
			value = "Looks up a ShopPromotion by Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Shop was found successfully", response = ShopResponse.class),
	})
	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getShop(
			@ApiParam(value = "The ShopPromotion's Id", required = true)
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@ApiOperation(
			value = "Looks up a ShopPromotion by Shop Id",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Shop was found successfully", response = ShopResponse.class),
	})
	@GetMapping(path = "queryOne/{shopId}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getByShopId(
			@ApiParam(value = "The Shop's Id", required = true)
			@PathVariable("shopId") Long shopId) {
		return ResponseEntity.ok(service.getByShopId(shopId));
	}


	@ApiOperation(
			value = "Looks up ALL ShopPromotions from the database",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Shops were found successfully", response = Object.class),
			@ApiResponse(code = 500, message = "Internal server error", response = Response.class),
	})
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object getAllShopPromotions() {
		return ResponseEntity.ok(service.getAllShopPromotions());
	}

	@ApiOperation(
			value = "Creates Shop Promotions",
			notes = "Self explanatory")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The Shop Promotions were created successfully", response = ShopResponse.class),
	})
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object createShopPromotion(@RequestBody List<ShopPromotionRequest> shopPromotionRequests) {
		return service.createShopPromotions(shopPromotionRequests);
	}

}