package com.uade.financialEntity.messages.responses;

@lombok.Getter
public class CardResponse implements com.uade.financialEntity.messages.Response {

    private Long cardId;
    private String cardName;
    private String cardImgUrl;
    private String cardDescription;
    private String cardLevel;
    private String cardType;

    private java.util.List<Long> turnsIds;

    public CardResponse(com.uade.financialEntity.models.Card card) {
        if(card != null){
            this.cardId = card.getCardId() != null ? card.getCardId() : null;
            this.cardName = card.getName() != null ? card.getName() : null;
            this.cardImgUrl = card.getImgUrl() != null ? card.getImgUrl() : null;
            this.cardDescription = card.getDescription() != null ? card.getDescription() : null;
            this.cardType = card.getOptionType().toString() != null ? card.getOptionType().toString() : null;
        }
    }

    public CardResponse() {
    }
}
