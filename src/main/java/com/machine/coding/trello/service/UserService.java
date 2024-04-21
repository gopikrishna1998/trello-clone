package com.machine.coding.trello.service;

import com.machine.coding.trello.model.User;
import com.machine.coding.trello.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String id) {
        return userRepository.getUser(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}
