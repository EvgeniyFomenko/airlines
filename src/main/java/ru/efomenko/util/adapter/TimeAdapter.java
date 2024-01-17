package ru.efomenko.util.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeAdapter extends TypeAdapter<LocalTime> {
    @Override
    public void write(JsonWriter jsonWriter, LocalTime localTime) throws IOException {

    }

    @Override
    public LocalTime read(JsonReader jsonReader) throws IOException {
        String time = jsonReader.nextString();
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm"));
    }
}
