package com.machine.coding.trello.model;

import com.google.gson.Gson;
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
public class CardList {
    UUID id;
    String name;
    transient Map<UUID, Card> cardMap;

    @Override
    public String toString() {
        Gson gson = GsonConfiguration.getGson();
        String jsonString = gson.toJson(this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        jsonObject.remove("cardMap");
        List<Card> cards = new ArrayList<>();
        if (!cardMap.isEmpty()) {
            for (Map.Entry<UUID, Card> cardEntry: cardMap.entrySet()) {
                cards.add(cardEntry.getValue());
            }
            JsonArray cardsJsonArray = gson.toJsonTree(cards).getAsJsonArray();
            jsonObject.add("cards", cardsJsonArray);
        }
        return gson.toJson(jsonObject);
    }
}
