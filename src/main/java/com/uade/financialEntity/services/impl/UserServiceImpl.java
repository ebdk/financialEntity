package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.UserRequest;
import com.uade.financialEntity.messages.responses.UserResponse;
import com.uade.financialEntity.models.User;
import com.uade.financialEntity.repositories.UserDAO;
import com.uade.financialEntity.services.UserService;
import com.uade.financialEntity.utils.Pair;
import com.uade.financialEntity.utils.PairObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userRepository;

	@Override
	public List<UserResponse> getAllusers() {
		List<User> personas = userRepository.findAll();
		return personas.stream().map(User::toDto).collect(Collectors.toList());
	}

	@Override
	public Object get(Long id) {
		Optional<User> persona = userRepository.findById(id);
		return persona.isPresent() ?
				new UserResponse(persona.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la persona con id " + id)).getMapMessage();
	}

	@Override
	public Object getByUsername(String username) {
		Optional<User> persona = userRepository.findByUserName(username);
		return persona.isPresent() ?
				new UserResponse(persona.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la persona con id " + username)).getMapMessage();
	}

	@Override
	public Object validateByUserNameAndPassword(String userName, String password) {
		Optional<User> persona = userRepository.findByUserName(userName);

		return persona.isPresent() ?
				(persona.get().getPassword().equals(password) ?
						new MessageResponse(new Pair("message", "Valido")).getMapMessage() :
						new MessageResponse(new Pair("message", "Invalido"),
								new Pair("error", "Usuario y contraseña no concuerdan")).getMapMessage()
				) :
				new MessageResponse(new Pair("message", "Invalido"),
						new Pair("error", "Usuario no encontrado")).getMapMessage();

	}

	@Override
	public Object createUsers(List<UserRequest> userRequests) {
		List<User> users = userRequests
				.stream()
				.map(UserRequest::toEntity)
				.collect(toList());

		userRepository.saveAll(users);
		return users.stream().map(User::toDto).collect(toList());
	}

	@Override
	public Object delete(Long cardId) {
		userRepository.deleteById(cardId);
		return new MessageResponse("Removed Succesfuly").getMapMessage();
	}

	@Override
	public Object modify(Long id, UserRequest request) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.modify(request);
			userRepository.save(user);
			return user.toFullDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada el user con id " + id)).getMapMessage();
		}
	}

	@Override
	public Object getCustomer(Long id) {
		Optional<User> persona = userRepository.findById(id);
		return persona.isPresent() ?
				persona.get().getCustomer().toFullDto() :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la persona con id " + id)).getMapMessage();
	}

	@Override
	public Object getShop(Long id) {
		Optional<User> persona = userRepository.findById(id);
		return persona.isPresent() ?
				persona.get().getShop().toFullDto() :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la persona con id " + id)).getMapMessage();
	}

	@Override
	public Object existsUsername(String username) {
		User user = new User(username);
		boolean exists = userRepository.exists(Example.of(user));
		return new MessageResponse(new PairObject("exists", exists)).getMapObject();
	}

}