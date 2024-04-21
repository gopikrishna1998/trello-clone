package com.machine.coding.trello.repository;

import com.machine.coding.trello.model.Card;
import com.machine.coding.trello.model.CardList;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

@Service
@Data
public class CardRepository {

    Map<UUID, Card> cardMap;

    public CardRepository() {
        cardMap = new HashMap<>();
    }

    public Card getCard(UUID id) {
        return cardMap.get(id);
    }

    public void addCard(Card card) {
        cardMap.put(card.getId(), card);
    }
}
