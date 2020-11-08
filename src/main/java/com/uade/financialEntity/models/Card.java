package com.uade.financialEntity.models;

@javax.persistence.Entity(name = "Card")
@javax.persistence.Table(name = "card")
@lombok.Getter
public class Card {

    //ATTRIBUTES
    @javax.persistence.Id
    @javax.persistence.Column(name="CARD_ID")
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    private Long cardId;
    private String name;
    private String imgUrl;
    private String description;
    private com.uade.financialEntity.models.Card.OptionType optionType;
    private com.uade.financialEntity.models.Card.TargetType targetType;
    private com.uade.financialEntity.models.Card.EffectType effectType;

    public enum OptionType {
        EVENT,
        OPPORTUNITY
    }

    public enum TargetType {
        PERSONAL,
        GLOBAL
    }

    public enum EffectType {
        TRANSACTION_ONLY,
        PROPERTY_BUY,
        SHARE_BUY,
        BOND_BUY,
        COMPANY_VALUE_CHANGE
    }


    //BUILDERS
    public Card(com.uade.financialEntity.messages.requests.CardRequest cardRequest) {
        this.name = cardRequest.getName();
        this.imgUrl = cardRequest.getImgUrl();
        this.description = cardRequest.getDescription();
        this.optionType = com.uade.financialEntity.models.Card.OptionType.valueOf(cardRequest.getOptionType());
        this.effectType = com.uade.financialEntity.models.Card.EffectType.valueOf(cardRequest.getEffectType());
        this.targetType = com.uade.financialEntity.models.Card.TargetType.valueOf(cardRequest.getTargetType());
    }

    public Card(com.uade.financialEntity.messages.requests.CardRequest cardRequest, String a) {
        this.name = cardRequest.getName();
        this.imgUrl = cardRequest.getImgUrl();
        this.description = cardRequest.getDescription();
        this.optionType = com.uade.financialEntity.models.Card.OptionType.valueOf(cardRequest.getOptionType());
        this.effectType = com.uade.financialEntity.models.Card.EffectType.valueOf(cardRequest.getEffectType());
        this.targetType = com.uade.financialEntity.models.Card.TargetType.valueOf(cardRequest.getTargetType());
    }


    /*
    public Card(CardRequest cardRequest, TransactionList transactionList) {
        this.name = cardRequest.getName();
        this.imgUrl = cardRequest.getImgUrl();
        this.description = cardRequest.getDescription();
        this.difficulty = GameDifficulty.valueOf(cardRequest.getDifficulty());
        this.optionType = OptionType.valueOf(cardRequest.getType());
        this.targetType = TargetType.valueOf(cardRequest.getType());
        this.transactionList = transactionList;
    }
     */

    public Card() {
    }

    //METHODS
    public com.uade.financialEntity.messages.responses.CardResponse toDto() {
        return new com.uade.financialEntity.messages.responses.CardResponse(this);
    }

}
