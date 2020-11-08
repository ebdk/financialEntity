/*package com.uade.financialEntity.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("financial_game/api/card")
public class CardController {

    @org.springframework.beans.factory.annotation.Autowired
    private com.uade.financialEntity.services.CardService service;

    @io.swagger.annotations.ApiOperation(
            value = "Gets a random card given difficulty and type",
            notes = "Self explanatory")
    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "The game was crated successfully", response = com.uade.financialEntity.messages.responses.UserResponse.class),
    })
    @org.springframework.web.bind.annotation.GetMapping(path="{type}/{difficulty}", produces = APPLICATION_JSON_VALUE)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Object getRandomCard(
            @io.swagger.annotations.ApiParam(value = "The card's type", required = true)
            @org.springframework.web.bind.annotation.PathVariable("type") String type,
            @io.swagger.annotations.ApiParam(value = "The card's difficulty, based on the game. GameDifficulties: EASY, MEDIUM, HARD", required = true)
            @org.springframework.web.bind.annotation.PathVariable("difficulty") String difficulty) {
        return service.getRandomCard(type, difficulty);
    }

    @io.swagger.annotations.ApiOperation(
            value = "Creates a card",
            notes = "Self explanatory")
    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "The card was crated successfully", response = com.uade.financialEntity.messages.responses.UserResponse.class),
    })
    @org.springframework.web.bind.annotation.PostMapping(produces = APPLICATION_JSON_VALUE)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Object createCard(@org.springframework.web.bind.annotation.RequestBody java.util.List<com.uade.financialEntity.messages.requests.CardRequest> cardRequest) {
        return service.createCard(cardRequest);
    }

}


 */