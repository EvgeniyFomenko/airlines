package ru.efomenko;

import ru.efomenko.service.CalculationService;
import ru.efomenko.util.FileWorker;
import ru.efomenko.util.JsonTicketsConverter;

import java.nio.file.Path;

public class App {
    private final static String pathToFile = "src/main/resources/tickets.json";
    private final static String from = "VVO";
    private final static String to = "TLV";

    public static void main(String[] args) {
        CalculationService calculationService = new CalculationService(JsonTicketsConverter.getTicketsFromJson(FileWorker.getContent(Path.of(pathToFile))), from, to);
        calculationService.calculateMinimalTimeFlight();
        calculationService.calculateMedianDifferenceAveragePrice();
    }
}
