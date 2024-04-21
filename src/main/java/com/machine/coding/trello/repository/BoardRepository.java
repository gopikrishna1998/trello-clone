package com.machine.coding.trello.repository;

import com.machine.coding.trello.model.Board;
import com.machine.coding.trello.model.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Data
public class BoardRepository {

    Map<UUID, Board> boardMap;

    public BoardRepository() {
        boardMap = new HashMap<>();
    }

    public void addBoard(Board board) {
        boardMap.put(board.getId(), board);
    }

    public Board deleteBoard(UUID id) {
        return boardMap.remove(id);
    }

    public Board getBoard(UUID id) {
        return boardMap.get(id);
    }

    public void updateBoard(UUID id, Board board) {
        boardMap.put(id, board);
    }

    public void addMember(UUID id, User user) {
        Board board = boardMap.get(id);
        Map<String, User> members = board.getMembers();
        members.put(user.getId(), user);
    }

    public void removeMember(UUID id, User user) {
        Board board = boardMap.get(id);
        Map<String, User> members = board.getMembers();
        members.remove(user.getId());
    }
}
