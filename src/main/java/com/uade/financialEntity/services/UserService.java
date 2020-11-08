package com.uade.financialEntity.services;

public interface UserService {

    java.util.List<com.uade.financialEntity.messages.responses.UserResponse> getAllusers();

    Object getByUsername(String username);

    Object validateByUserNameAndPassword(String userName, String password);

    Object createUser(com.uade.financialEntity.messages.requests.UserRequest userRequest);

    Object updateCoins(String username, Integer coinsValue);
}
