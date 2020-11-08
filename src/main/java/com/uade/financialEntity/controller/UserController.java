package com.uade.financialEntity.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("financial_game/api/user")
public class UserController {

    @org.springframework.beans.factory.annotation.Autowired
    private com.uade.financialEntity.services.UserService service;

    @io.swagger.annotations.ApiOperation(
            value = "Looks up a user by username",
            notes = "Self explanatory")
    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "The user was found successfully", response = com.uade.financialEntity.messages.responses.UserResponse.class),
    })
    @org.springframework.web.bind.annotation.GetMapping(path="{username}", produces = APPLICATION_JSON_VALUE)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Object getUser(
            @io.swagger.annotations.ApiParam(value = "The user's username", required = true)
            @org.springframework.web.bind.annotation.PathVariable("username") String username) {
        return org.springframework.http.ResponseEntity.ok(service.getByUsername(username));
    }


    @io.swagger.annotations.ApiOperation(
            value = "Looks up ALL users from the database",
            notes = "Self explanatory")
    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "The users were found successfully", response = Object.class),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal server error", response = com.uade.financialEntity.messages.Response.class),
    })
    @org.springframework.web.bind.annotation.GetMapping(produces = APPLICATION_JSON_VALUE)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Object getAllUsers() {
        return org.springframework.http.ResponseEntity.ok(service.getAllusers());
    }


    @io.swagger.annotations.ApiOperation(
            value = "Validates data from User",
            notes = "Looks up the user and tries to match it's password with the one given")
    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "The user was validated successfully", response = Object.class),
    })
    @org.springframework.web.bind.annotation.GetMapping(path="validate/{username}/{password}", produces = APPLICATION_JSON_VALUE)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Object getValidation(
            @io.swagger.annotations.ApiParam(value = "The user's username", required = true)
            @org.springframework.web.bind.annotation.PathVariable("username") String username,
            @io.swagger.annotations.ApiParam(value = "The user's password", required = true)
            @org.springframework.web.bind.annotation.PathVariable("password") String password) {
        return org.springframework.http.ResponseEntity.ok(service.validateByUserNameAndPassword(username, password));
    }

    @io.swagger.annotations.ApiOperation(
            value = "Creates a user",
            notes = "Self explanatory")
    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "The user was crated successfully", response = com.uade.financialEntity.messages.responses.UserResponse.class),
    })
    @org.springframework.web.bind.annotation.PostMapping(produces = APPLICATION_JSON_VALUE)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Object createPersona(@org.springframework.web.bind.annotation.RequestBody com.uade.financialEntity.messages.requests.UserRequest userRequest) {
        return service.createUser(userRequest);
    }

    @io.swagger.annotations.ApiOperation(
            value = "Looks up a user by username, and adds or substracts the coins",
            notes = "Self explanatory")
    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "The user was found and modified successfully ", response = com.uade.financialEntity.messages.responses.UserResponse.class),
    })
    @org.springframework.web.bind.annotation.PostMapping(path="{username}/{coins_value}", produces = APPLICATION_JSON_VALUE)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Object updateCoins(
            @io.swagger.annotations.ApiParam(value = "The user's username", required = true)
            @org.springframework.web.bind.annotation.PathVariable("username") String username,
            @io.swagger.annotations.ApiParam(value = "The coins to be added or substracted", required = true)
            @org.springframework.web.bind.annotation.PathVariable("coins_value") String coinsValue) {
        return org.springframework.http.ResponseEntity.ok(service.updateCoins(username, Integer.valueOf(coinsValue)));
    }

}
