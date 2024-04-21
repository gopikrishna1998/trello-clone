package com.machine.coding.trello.service;

import com.machine.coding.trello.model.Card;
import com.machine.coding.trello.model.CardList;
import com.machine.coding.trello.model.User;
import com.machine.coding.trello.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CardService {

    CardRepository cardRepository;
    UserService userService;
    CardListService cardListService;

    public CardService(CardRepository cardRepository,
                       UserService userService,
                       CardListService cardListService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
        this.cardListService = cardListService;
    }

    public void showCard(String cardIdString) {
        UUID id = UUID.fromString(cardIdString);
        Card card = cardRepository.getCard(id);
        if (card != null) {
            log.info(card.toString());
        } else {
            log.info("Card " + id + " does not exist");
        }
    }

    public void createCard(String cardListIdString, String cardName) {
        UUID id = UuidService.getStaticUuid();
        UUID cardListId = UUID.fromString(cardListIdString);
        CardList cardList = cardListService.getCardList(cardListId);
        Card card = new Card(id, cardName, null, null, cardList);
        cardRepository.addCard(card);
        cardListService.addCardToCardList(cardListId, card);
        log.info("Created card: " + id);
    }

    public void assignCard(String cardId, String userEmailId) {
        UUID id = UUID.fromString(cardId);
        Card card = cardRepository.getCard(id);
        User user = userService.getUserByEmail(userEmailId);
        if (card == null) {
            log.info("Card " + id + " does not exist");
        } else if (user == null) {
            log.info("User with email " + userEmailId + " does not exist");
        } else {
            card.setAssignedTo(userEmailId);
            cardRepository.addCard(card);
        }
    }

    public void unassignCard(String cardId) {
        UUID id = UUID.fromString(cardId);
        Card card = cardRepository.getCard(id);
        if (card == null) {
            log.info("Card " + id + " does not exist");
        } else {
            card.setAssignedTo(null);
            cardRepository.addCard(card);
        }
    }

    public void moveCard(String cardIdString, String cardListIdString) {
        UUID cardId = UUID.fromString(cardIdString);
        UUID cardListId = UUID.fromString(cardListIdString);
        Card card = cardRepository.getCard(cardId);
        CardList cardList = cardListService.getCardList(cardListId);
        if (card == null) {
            log.info("Card " + cardId + " does not exist");
        } else if (cardList == null) {
            log.info("CardList " + cardListId + " does not exist");
        } else {
            cardListService.moveCard(card, cardList);
        }
    }

    public void updateCard(String cardIdString, String cardFieldName, String cardFieldValue) {
        UUID cardId = UUID.fromString(cardIdString);
        Card card = cardRepository.getCard(cardId);
        if (card == null) {
            log.info("Card " + cardId + " does not exist");
        } else {
            if (cardFieldName.equals("name")) {
                card.setName(cardFieldValue);
            } else if (cardFieldName.equals("description")) {
                card.setDescription(cardFieldValue);
            }
            cardRepository.addCard(card);
        }
    }
}
