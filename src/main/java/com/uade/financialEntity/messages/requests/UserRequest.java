package com.uade.financialEntity.messages.requests;

@lombok.Getter
public class UserRequest implements com.uade.financialEntity.messages.Response {

    //ATTRIBUTES
    private String userName;
    private String password;
    private String rank;
    private Integer coins;

    //BUILDERS
    public UserRequest(com.uade.financialEntity.models.User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.rank = user.getRank().toString();
        this.coins = user.getCoins();
    }

    public UserRequest() {
    }

}
