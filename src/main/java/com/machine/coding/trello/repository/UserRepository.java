package com.machine.coding.trello.repository;

import com.machine.coding.trello.model.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class UserRepository {

    Map<String, User> userMap;

    public UserRepository() {
        userMap = new HashMap<>();
        userMap.put("user1", new User("user1", "Gaurav Chandak", "gaurav@workat.tech"));
        userMap.put("user3", new User("user3", "Sagar Jain", "sagar@workat.tech"));
        userMap.put("user2", new User("user2", "Gopikrishna K S", "gopi@workat.tech"));
    }

    public User getUser(String id) {
        return userMap.get(id);
    }

    public User getUserByEmail(String email) {
        for(Map.Entry<String, User> userEntry: userMap.entrySet()) {
            if (userEntry.getValue().getEmail().equals(email)) {
                return userEntry.getValue();
            }
        }
        return null;
    }
}
