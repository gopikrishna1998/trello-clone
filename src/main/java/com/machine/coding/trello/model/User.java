package com.machine.coding.trello.model;

import com.google.gson.Gson;
import com.machine.coding.trello.config.GsonConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    String id;
    String name;
    String email;

    @Override
    public String toString() {
        Gson gson = GsonConfiguration.getGson();
        return gson.toJson(this);
    }
}
