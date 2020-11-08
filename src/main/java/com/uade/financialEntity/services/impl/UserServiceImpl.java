package com.uade.financialEntity.services.impl;

@org.springframework.stereotype.Service
public class UserServiceImpl implements com.uade.financialEntity.services.UserService {

    @org.springframework.beans.factory.annotation.Autowired
    private com.uade.financialEntity.repositories.UserDAO userRepository;

    @Override
    public java.util.List<com.uade.financialEntity.messages.responses.UserResponse> getAllusers() {
        java.util.List<com.uade.financialEntity.models.User> personas = userRepository.findAll();
        return personas.stream().map(com.uade.financialEntity.models.User::toDto).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Object getByUsername(String username) {
        java.util.Optional<com.uade.financialEntity.models.User> persona = userRepository.findByUserName(username);
        return persona.isPresent() ?
                new com.uade.financialEntity.messages.responses.UserResponse(persona.get()) :
                new com.uade.financialEntity.messages.MessageResponse(new com.uade.financialEntity.utils.Pair("error", "Error, no pudo ser encontrada la persona con id " + username)).getMapMessage();
    }

    @Override
    public Object validateByUserNameAndPassword(String userName, String password) {
        java.util.Optional<com.uade.financialEntity.models.User> persona = userRepository.findByUserName(userName);

        return persona.isPresent() ?
                (persona.get().getPassword().equals(password) ?
                        new com.uade.financialEntity.messages.MessageResponse(new com.uade.financialEntity.utils.Pair("message", "Valido")).getMapMessage() :
                        new com.uade.financialEntity.messages.MessageResponse(new com.uade.financialEntity.utils.Pair("message", "Invalido"),
                                new com.uade.financialEntity.utils.Pair("error", "Usuario y contraseña no concuerdan")).getMapMessage()
                        ) :
                new com.uade.financialEntity.messages.MessageResponse(new com.uade.financialEntity.utils.Pair("message", "Invalido"),
                        new com.uade.financialEntity.utils.Pair("error", "Usuario no encontrado")).getMapMessage();

    }

    @Override
    public Object createUser(com.uade.financialEntity.messages.requests.UserRequest userRequest) {
        com.uade.financialEntity.models.User newUser = new com.uade.financialEntity.models.User(userRequest);
        userRepository.save(newUser);
        return newUser.toDto();
    }

    @Override
    public Object updateCoins(String username, Integer coinsValue) {
        Integer result = null;
        java.util.Optional<com.uade.financialEntity.models.User> userSearch = userRepository.findByUserName(username);
        if(userSearch.isPresent()){
            com.uade.financialEntity.models.User user = userSearch.get();
            result = user.getCoins() + coinsValue;
            user.setCoins(result);
            userRepository.save(user);
        } else {
            new com.uade.financialEntity.messages.MessageResponse(new com.uade.financialEntity.utils.Pair("message", "Invalido"),
                    new com.uade.financialEntity.utils.Pair("error", "Usuario no encontrado")).getMapMessage();
        }
        return result;
    }


}
