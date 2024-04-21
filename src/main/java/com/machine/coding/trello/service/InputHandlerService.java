package com.machine.coding.trello.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class InputHandlerService {

    BoardService boardService;
    CardListService cardListService;
    CardService cardService;

    public InputHandlerService(BoardService boardService,
                               CardListService cardListService,
                               CardService cardService) {
        this.boardService = boardService;
        this.cardListService = cardListService;
        this.cardService = cardService;
    }

    public void handleInput(String inputLine) {
//        log.info("Test : " + inputLine);
        List<String> words = new ArrayList<>(Arrays.asList(inputLine.split("\\s+")));
        List<String> updatedWords = handleSpaceInName(words);
        String firstWord = getWordFromList(updatedWords, 0);
        String secondWord = getWordFromList(updatedWords, 1);
        String thirdWord = getWordFromList(updatedWords, 2);
        String fourthWord = getWordFromList(updatedWords, 3);
        if (updatedWords.size() == 1 && firstWord != null && firstWord.equals("SHOW")) {
            boardService.printAllBoards();
        } else if (updatedWords.size() == 3) {
            if (firstWord != null && firstWord.equals("BOARD")) {
                if (secondWord != null && secondWord.equals("CREATE")) {
                    boardService.createBoard(thirdWord);
                } else if (secondWord != null && secondWord.equals("DELETE")) {
                    boardService.deleteBoard(thirdWord);
                }
            } else if (firstWord != null && firstWord.equals("SHOW") &&
                    secondWord != null && secondWord.equals("BOARD") &&
                    thirdWord != null) {
                boardService.showBoard(thirdWord);
            } else if (firstWord != null && firstWord.equals("SHOW") &&
                    secondWord != null && secondWord.equals("LIST")) {
                cardListService.showList(thirdWord);
            } else if (firstWord != null && firstWord.equals("SHOW") &&
                    secondWord != null && secondWord.equals("CARD")) {
                cardService.showCard(thirdWord);
            } else if (firstWord != null && firstWord.equals("CARD") &&
                    thirdWord != null && thirdWord.equals("UNASSIGN")) {
                cardService.unassignCard(secondWord);
            }
        } else if (updatedWords.size() == 4) {
            if (firstWord != null && firstWord.equals("BOARD")) {
                if (thirdWord != null && thirdWord.equals("ADD_MEMBER")) {
                    boardService.addMember(secondWord, fourthWord);
                } else if (thirdWord != null &&
                        thirdWord.equals("REMOVE_MEMBER")) {
                    boardService.removeMember(secondWord, fourthWord);
                } else {
                    boardService.updateBoard(secondWord, thirdWord, fourthWord);
                }
            } else if (firstWord != null && firstWord.equals("LIST")) {
                if (secondWord != null && secondWord.equals("CREATE")) {
                    cardListService.createCardList(thirdWord, fourthWord);
                } else {
                    cardListService.updateCardList(secondWord, thirdWord, fourthWord);
                }
            } else if (firstWord != null && firstWord.equals("CARD")) {
                if (secondWord != null && secondWord.equals("CREATE")) {
                    cardService.createCard(thirdWord, fourthWord);
                } else if (thirdWord != null && thirdWord.equals("ASSIGN")) {
                    cardService.assignCard(secondWord, fourthWord);
                } else if (thirdWord != null && thirdWord.equals("MOVE")) {
                    cardService.moveCard(secondWord, fourthWord);
                } else {
                    cardService.updateCard(secondWord, thirdWord, fourthWord);
                }
            }
        }
    }

    private String getWordFromList(List<String> words, int index) {
        try {
            return words.get(index);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return null;
        }
    }

    private List<String> handleSpaceInName(List<String> words) {
        List<String> updatedWords = new ArrayList<>(words);
        String firstWord = getWordFromList(updatedWords, 0);
        String secondWord = getWordFromList(updatedWords, 1);
        String thirdWord = getWordFromList(updatedWords, 2);
        if (firstWord != null && firstWord.equals("BOARD")) {
            // Handling space in board name in create board
            if (secondWord != null && secondWord.equals("CREATE")) {
                StringBuilder updatedName = new StringBuilder();
                for (int i = 2; i < updatedWords.size(); i++) {
                    if (i == updatedWords.size() - 1) {
                        updatedName.append(updatedWords.get(i));
                    } else {
                        updatedName.append(updatedWords.get(i)).append(" ");
                    }
                }
                updatedWords = updatedWords.subList(0, 2);
                updatedWords.add(updatedName.toString());
                // Handling space in board name in update board
            } else if (thirdWord != null && thirdWord.equals("name")) {
                StringBuilder updatedName = new StringBuilder();
                for (int i = 3; i < updatedWords.size(); i++) {
                    if (i == updatedWords.size() - 1) {
                        updatedName.append(updatedWords.get(i));
                    } else {
                        updatedName.append(updatedWords.get(i)).append(" ");
                    }
                }
                updatedWords = updatedWords.subList(0, 3);
                updatedWords.add(updatedName.toString());
            }
        } else if (firstWord != null && firstWord.equals("LIST")) {
            // Handling space in list name in create list, update list
            StringBuilder updatedName = new StringBuilder();
            for (int i = 3; i < updatedWords.size(); i++) {
                if (i == updatedWords.size() - 1) {
                    updatedName.append(updatedWords.get(i));
                } else {
                    updatedName.append(updatedWords.get(i)).append(" ");
                }
            }
            updatedWords = updatedWords.subList(0, 3);
            updatedWords.add(updatedName.toString());
        } else if (firstWord != null && firstWord.equals("CARD")) {
            if (thirdWord != null && !thirdWord.equals("ASSIGN")
                    && !thirdWord.equals("MOVE") && !thirdWord.equals("UNASSIGN")) {
                StringBuilder updatedName = new StringBuilder();
                for (int i = 3; i < updatedWords.size(); i++) {
                    if (i == updatedWords.size() - 1) {
                        updatedName.append(updatedWords.get(i));
                    } else {
                        updatedName.append(updatedWords.get(i)).append(" ");
                    }
                }
                updatedWords = updatedWords.subList(0, 3);
                updatedWords.add(updatedName.toString());
            }
        }
        return updatedWords;
    }
}
