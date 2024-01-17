package ru.efomenko;

import ru.efomenko.service.CalculationService;
import ru.efomenko.util.FileWorker;
import ru.efomenko.util.JsonTicketsConverter;

import java.nio.file.Path;

public class App {
    private final static String pathToFile = "src/main/resources/tickets.json";

    public static void main(String[] args) {
        CalculationService calculationService = new CalculationService(JsonTicketsConverter.getTicketsFromJson(FileWorker.getContent(Path.of(pathToFile))));
        calculationService.calculateMinimalTimeFlight();
        calculationService.calculateMedianDifferenceAveragePrice();
    }
}
