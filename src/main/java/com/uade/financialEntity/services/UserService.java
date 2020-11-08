package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.UserRequest;
import com.uade.financialEntity.messages.responses.UserResponse;

import java.util.List;

public interface UserService {

	List<UserResponse> getAllusers();

	Object get(Long id);

	Object getByUsername(String username);

	Object validateByUserNameAndPassword(String userName, String password);

	Object createUser(UserRequest userRequest);

}
