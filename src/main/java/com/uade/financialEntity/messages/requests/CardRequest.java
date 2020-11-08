package com.uade.financialEntity.messages.requests;

@lombok.Getter
public class CardRequest {

    private String name;
    private String imgUrl;
    private String description;
    private String difficulty;
    private String optionType;
    private String targetType;
    private String effectType;

    public CardRequest(com.uade.financialEntity.models.Card card) {
        this.name = card.getName();
        this.imgUrl = card.getImgUrl();
        this.description = card.getDescription();
        //this.type = card.getOptionType().toString();
    }

    public CardRequest() {
    }

    public com.uade.financialEntity.models.Card toEntity(String a) {
        return new com.uade.financialEntity.models.Card(this, a);
    }

    public com.uade.financialEntity.models.Card toEntity() {
        return new com.uade.financialEntity.models.Card(this);
    }

}
