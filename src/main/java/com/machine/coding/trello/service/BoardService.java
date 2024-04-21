package com.machine.coding.trello.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.machine.coding.trello.config.GsonConfiguration;
import com.machine.coding.trello.model.Board;
import com.machine.coding.trello.model.CardList;
import com.machine.coding.trello.model.Privacy;
import com.machine.coding.trello.model.User;
import com.machine.coding.trello.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BoardService {

    BoardRepository boardRepository;
    UserService userService;

    public BoardService(BoardRepository boardRepository,
                        UserService userService) {
        this.boardRepository = boardRepository;
        this.userService = userService;
    }

    public void printAllBoards() {
        Map<UUID, Board> boardMap = boardRepository.getBoardMap();
        JsonArray jsonArray = new JsonArray();
        Gson gson = GsonConfiguration.getGson();
        if (boardMap.isEmpty()) {
            log.info("No boards");
        } else {
            for (Map.Entry<UUID, Board> entry: boardMap.entrySet()) {
                String jsonString = gson.toJson(entry.getValue());
                JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
                Map<String, User> members = entry.getValue().getMembers();
                Map<UUID, CardList> listMap = entry.getValue().getLists();
                List<User> membersList = new ArrayList<>();
                List<CardList> lists = new ArrayList<>();
                jsonObject.remove("members");
                jsonObject.remove("lists");
                if (!members.isEmpty()) {
                    for (Map.Entry<String, User> userEntry: members.entrySet()) {
                        membersList.add(userEntry.getValue());
                    }
                    JsonArray membersJsonArray = gson.toJsonTree(membersList).getAsJsonArray();
                    jsonObject.add("members", membersJsonArray);
                }
                if (!listMap.isEmpty()) {
                    for (Map.Entry<UUID, CardList> listEntry: listMap.entrySet()) {
                        lists.add(listEntry.getValue());
                    }
                    JsonArray listJsonArray = gson.toJsonTree(lists).getAsJsonArray();
                    jsonObject.add("lists", listJsonArray);
                }
                jsonArray.add(jsonObject);
            }
            log.info(GsonConfiguration.getGson().toJson(jsonArray));
        }
    }

    public void createBoard(String boardName) {
        UUID id = UuidService.getStaticUuid();
        Board board = new Board(id, boardName, Privacy.PUBLIC, null, new HashMap<>(), new HashMap<>());
        boardRepository.addBoard(board);
        log.info("Created board: " + id);
    }

    public void deleteBoard(String boardIdString) {
        UUID id = UUID.fromString(boardIdString);
        Board board = boardRepository.deleteBoard(id);
        if (board == null) {
            log.info("Board " + id + " does not exist");
        }
    }

    public void showBoard(String boardIdString) {
        UUID id = UUID.fromString(boardIdString);
        Board board = boardRepository.getBoard(id);
        if (board != null) {
            log.info(board.toString());
        } else {
            log.info("Board " + id + " does not exist");
        }
    }

    public void addMember(String boardIdString, String userId) {
        UUID boardId = UUID.fromString(boardIdString);
        Board board = boardRepository.getBoard(boardId);
        User user = userService.getUser(userId);
        if (board == null) {
            log.info("Board " + boardId + " does not exist");
        } else if (user == null) {
            log.info("User " + userId + " does not exist");
        } else {
            boardRepository.addMember(boardId, user);
        }
    }

    public void removeMember(String boardIdString, String userId) {
        UUID boardId = UUID.fromString(boardIdString);
        Board board = boardRepository.getBoard(boardId);
        User user = userService.getUser(userId);
        if (board == null) {
            log.info("Board " + boardId + " does not exist");
        } else if (user == null) {
            log.info("User " + userId + " does not exist");
        } else {
            boardRepository.removeMember(boardId, user);
        }
    }

    public void updateBoard(String boardIdString, String boardField, String boardValue) {
        UUID boardId = UUID.fromString(boardIdString);
        Board board = boardRepository.getBoard(boardId);
        if (board == null) {
            log.info("Board " + boardId + " does not exist");
        } else {
            if (boardField.equals("name")) {
                board.setName(boardValue);
            } else if (boardField.equals("privacy")) {
                board.setPrivacy(Privacy.valueOf(boardValue));
            }
            boardRepository.updateBoard(boardId, board);
        }
    }

    public void assignCardListToBoard(CardList cardList, String boardIdString) {
        UUID boardId = UUID.fromString(boardIdString);
        Board board = boardRepository.getBoard(boardId);
        if (board == null) {
            log.info("Board " + boardId + " does not exist");
        } else {
            board.getLists().put(cardList.getId(), cardList);
            boardRepository.updateBoard(boardId, board);
        }
    }
}
