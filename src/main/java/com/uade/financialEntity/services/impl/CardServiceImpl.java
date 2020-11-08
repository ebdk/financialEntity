package com.uade.financialEntity.services.impl;

import static java.util.stream.Collectors.toList;

@org.springframework.stereotype.Service
public class CardServiceImpl implements com.uade.financialEntity.services.CardService {

    @org.springframework.beans.factory.annotation.Autowired
    private com.uade.financialEntity.repositories.CardDAO cardRepository;

    @Override
    public Object getRandomCard(String cardType, String cardDifficulty) {
        java.util.List<com.uade.financialEntity.models.Card> allCards = cardRepository.findAll();
        java.util.List<com.uade.financialEntity.models.Card> filteredCards  = allCards
                .stream()
                .filter(x -> (cardType.equals(x.getOptionType().toString())))
                .collect(toList());
        java.util.Random rand = new java.util.Random();
        if(!(filteredCards.isEmpty())){
            return filteredCards.get(rand.nextInt(filteredCards.size())).toDto();
        } else {
            java.util.List<com.uade.financialEntity.models.Card> sameTypeCards  = allCards
                    .stream()
                    .filter(x -> cardType.equals(x.getOptionType().toString()))
                    .collect(toList());
            return sameTypeCards.get(rand.nextInt(sameTypeCards.size())).toDto();
        }
    }


    @Override
    public Object createCard(java.util.List<com.uade.financialEntity.messages.requests.CardRequest> cardRequestList) {

        java.util.List<com.uade.financialEntity.models.Card> cards = cardRequestList
                .stream()
                .map(cardRequest -> cardRequest.toEntity("a"))
                .collect(toList());

        cardRepository.saveAll(cards);
        return cards.stream().map(com.uade.financialEntity.models.Card::toDto).collect(toList());
    }

}
