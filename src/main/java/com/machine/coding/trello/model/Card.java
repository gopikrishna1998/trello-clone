package com.machine.coding.trello.model;

import com.google.gson.Gson;
import com.machine.coding.trello.config.GsonConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Card {
    UUID id;
    String name;
    String description;
    String assignedTo;
    transient CardList cardList;

    @Override
    public String toString() {
        Gson gson = GsonConfiguration.getGson();
        return gson.toJson(this);
    }
}
