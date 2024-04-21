package com.machine.coding.trello.service;

import com.machine.coding.trello.model.Card;
import com.machine.coding.trello.model.CardList;
import com.machine.coding.trello.repository.CardListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class CardListService {

    CardListRepository cardListRepository;
    BoardService boardService;

    public CardListService(CardListRepository cardListRepository,
                           BoardService boardService) {
        this.cardListRepository = cardListRepository;
        this.boardService = boardService;
    }

    public void showList(String listIdString) {
        UUID id = UUID.fromString(listIdString);
        CardList cardList = cardListRepository.getCardList(id);
        if (cardList != null) {
            log.info(cardList.toString());
        } else {
            log.info("CardList " + id + " does not exist");
        }
    }

    public void createCardList(String boardIdString, String cardListName) {
        UUID cardListId = UuidService.getStaticUuid();
        CardList cardList = new CardList(cardListId, cardListName, new HashMap<>());
        cardListRepository.addCardList(cardList);
        boardService.assignCardListToBoard(cardList, boardIdString);
        log.info("Created list: " + cardListId);
    }

    public void updateCardList(String cardListIdString, String cardListFieldName,
                               String cardListFieldValue) {
        UUID cardListId = UUID.fromString(cardListIdString);
        CardList cardList = cardListRepository.getCardList(cardListId);
        if (cardList != null) {
            if (cardListFieldName.equals("name")) {
                cardList.setName(cardListFieldValue);
            }
            cardListRepository.addCardList(cardList);
        } else {
            log.info("CardList " + cardListId + " does not exist");
        }
    }

    public void addCardToCardList(UUID cardListId, Card card) {
        CardList cardList = cardListRepository.getCardList(cardListId);
        Map<UUID, Card> cardMap = cardList.getCardMap();
        cardMap.put(card.getId(), card);
    }

    public CardList getCardList(UUID cardListId) {
        return cardListRepository.getCardList(cardListId);
    }

    public void moveCard(Card card, CardList newCardList) {
        CardList existingCardList = card.getCardList();
        existingCardList.getCardMap().remove(card.getId());
        newCardList.getCardMap().put(card.getId(), card);
    }
}
