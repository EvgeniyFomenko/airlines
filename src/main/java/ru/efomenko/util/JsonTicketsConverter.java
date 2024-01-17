package ru.efomenko.util;

import com.google.gson.*;
import ru.efomenko.pojo.Ticket;
import ru.efomenko.util.adapter.DateAdapter;
import ru.efomenko.util.adapter.TimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class JsonTicketsConverter {
    public static List<Ticket> getTicketsFromJson(String jsonTicket) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(LocalDate.class, new DateAdapter())
                .registerTypeAdapter(LocalTime.class, new TimeAdapter()).create();
        JsonElement jsonElement = JsonParser.parseString(jsonTicket);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        List<Ticket> targetCollection = Arrays.asList(gson.fromJson(jsonObject.getAsJsonArray("tickets"), Ticket[].class));

        return targetCollection;
    }
}
