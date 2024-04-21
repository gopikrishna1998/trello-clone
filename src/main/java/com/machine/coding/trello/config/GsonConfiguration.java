package com.machine.coding.trello.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonConfiguration {

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            return new GsonBuilder().create();
        }
        return gson;
    }
}
