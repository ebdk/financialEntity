package com.uade.financialEntity.messages.responses;

@lombok.Getter
public class UserResponse implements com.uade.financialEntity.messages.Response {

    //ATTRIBUTES
    private Long userId;
    private String userName;
    private String password;
    private String rank;
    private Integer coins;
    private java.util.List<Long> playerIds;

    //BUILDERS
    public UserResponse(com.uade.financialEntity.models.User user) {
        if(user != null){
            this.userId = user.getUserId() != null ? user.getUserId() : null;
            this.userName = user.getUserName() != null ? user.getUserName() : null;
            this.password = user.getPassword() != null ? user.getPassword() : null;
            this.rank = user.getRank() != null ? user.getRank().toString() : null;
            this.coins = user.getCoins();
        }
    }

    public UserResponse() {
    }

}
