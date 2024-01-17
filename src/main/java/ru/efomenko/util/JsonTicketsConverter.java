package ru.efomenko.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ru.efomenko.pojo.Ticket;
import ru.efomenko.util.adapter.DateAdapter;
import ru.efomenko.util.adapter.TimeAdapter;


import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JsonTicketsConverter {
    public static List<Ticket> getTicketsFromJson(String jsonTicket) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class,new DateAdapter())
                .registerTypeAdapter(LocalTime.class,new TimeAdapter());
        Gson gson =gsonBuilder.create();
        JsonElement jsonElement = JsonParser.parseString(jsonTicket);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
//        Ticket[] tickets =  ;
//        Type targetClassType = new TypeToken<List<Ticket>>(){}.getType();
        List<Ticket> targetCollection =  Arrays.asList(gson.fromJson(jsonObject.getAsJsonArray("tickets"), Ticket[].class));
        return targetCollection;
    }
}
