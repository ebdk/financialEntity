package com.uade.financialEntity.models;

@javax.persistence.Entity(name = "User")
@javax.persistence.Table(name = "user")
@lombok.Getter
@lombok.Setter
public class User {

    //ATTRIBUTES
    @javax.persistence.Id
    @javax.persistence.Column(name="USER_ID")
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String password;
    private Integer coins;
    private com.uade.financialEntity.models.User.UserRank rank;

    public enum UserRank {
        BEGGINER,
        PROFESSIONAL,
        ECONOMIST,
        CPU
    }


    //BUILDERS
    public User(com.uade.financialEntity.messages.requests.UserRequest userRequest) {
        this.userName = userRequest.getUserName();
        this.password = userRequest.getPassword();
        this.rank = userRequest.getRank() != null ? com.uade.financialEntity.models.User.UserRank.valueOf(userRequest.getRank()) : com.uade.financialEntity.models.User.UserRank.BEGGINER;
        this.coins = (userRequest.getCoins() == null || userRequest.getCoins() == 0) ? 1000 : userRequest.getCoins();
    }

    public User() {
    }

    //METHODS
    public com.uade.financialEntity.messages.responses.UserResponse toDto() {
        return new com.uade.financialEntity.messages.responses.UserResponse(this);
    }

}
