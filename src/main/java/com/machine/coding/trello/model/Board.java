package com.machine.coding.trello.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.machine.coding.trello.config.GsonConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Board {
    UUID id;
    String name;
    Privacy privacy;
    String url;
    Map<String, User> members;
    Map<UUID, CardList> lists;

    @Override
    public String toString() {
        Gson gson = GsonConfiguration.getGson();
        String jsonString = gson.toJson(this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        jsonObject.remove("members");
        jsonObject.remove("lists");
        jsonObject.remove("cardMap");
        List<User> membersList = new ArrayList<>();
        List<CardList> cardLists = new ArrayList<>();
        if (!members.isEmpty()) {
            for (Map.Entry<String, User> userEntry: members.entrySet()) {
                membersList.add(userEntry.getValue());
            }
            JsonArray membersJsonArray = gson.toJsonTree(membersList).getAsJsonArray();
            jsonObject.add("members", membersJsonArray);
        }
        if (!lists.isEmpty()) {
            for (Map.Entry<UUID, CardList> cardListEntry: lists.entrySet()) {
                cardLists.add(cardListEntry.getValue());
            }
            JsonArray listsJsonArray = gson.toJsonTree(cardLists).getAsJsonArray();
            jsonObject.add("lists", listsJsonArray);
        }
        return gson.toJson(jsonObject);
    }
}
