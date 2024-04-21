package com.machine.coding.trello.repository;

import com.machine.coding.trello.model.CardList;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Data
public class CardListRepository {

    Map<UUID, CardList> cardListMap;

    public CardListRepository() {
        this.cardListMap = new HashMap<>();
    }

    public CardList getCardList(UUID id) {
        return cardListMap.get(id);
    }

    public void addCardList(CardList cardList) {
        cardListMap.put(cardList.getId(), cardList);
    }
}
