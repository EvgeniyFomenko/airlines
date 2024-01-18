package ru.efomenko.service;

import ru.efomenko.pojo.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CalculationService {
    private final List<Ticket> ticketList;


    public CalculationService(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void calculateMedianDifferenceAveragePrice() {
        String from = "VVO";
        String to = "TLV";
        AtomicInteger sum = new AtomicInteger();
        if(ticketList.isEmpty()){
            return;
        }
        List<Ticket> sortTickets = ticketList.stream().filter(ticket -> ticket.getOrigin().equals(from) && ticket.getDestination().equals(to))
                .sorted(Comparator.comparingInt(Ticket::getPrice)).collect(Collectors.toList());
        int averageNumber = 2;
        int medinePrice = sortTickets.get(((sortTickets.size()-1) / averageNumber)).getPrice();

        sortTickets.forEach(ticket -> sum.addAndGet(ticket.getPrice()));

        long countTickets = sortTickets.size();
        long averagePrice = sum.get() / countTickets;
        System.out.println(String.format("Разница медины и средней цены из %s в %s составляет %d", from, to,Math.abs(medinePrice - averagePrice) ));


    }

    public void calculateMinimalTimeFlight() {
        String from = "VVO";
        String to = "TLV";
        Map<String, Duration> shortTimeArrived = new HashMap<>();
        if(ticketList.isEmpty()){
            return;
        }
        ticketList.stream().filter(ticket -> ticket.getOrigin().equals(from) && ticket.getDestination().equals(to))
                .peek(ticket -> shortTimeArrived.putIfAbsent(ticket.getCarrier(), getDurationTicket(ticket)))
                .forEach(ticket -> shortTimeArrived.put(ticket.getCarrier(), getShortDuration(getDurationTicket(ticket), shortTimeArrived.get(ticket.getCarrier()))));

        shortTimeArrived.keySet().forEach(key -> printResult(key, shortTimeArrived, from, to));
    }

    private static void printResult(String car, Map<String, Duration> shortTimeArrived, String from, String to) {
        Duration duration = shortTimeArrived.get(car);
        System.out.println(String.format("Продолжительность самого короткого поерелёта из %s в %s перевозчика %s составила %d часов %d минут", from, to, car, duration.toHoursPart(), duration.toMinutesPart()));
    }

    private Duration getDurationTicket(Ticket ticket) {
        LocalDateTime DepartureDateTime = LocalDateTime.of(ticket.getDeparture_date(), ticket.getDeparture_time());
        LocalDateTime ArrivalDateTime = LocalDateTime.of(ticket.getArrival_date(), ticket.getArrival_time());
        return Duration.between(DepartureDateTime, ArrivalDateTime);
    }

    private Duration getShortDuration(Duration incomingDuration, Duration saveDuration) {
        if (saveDuration.toSeconds() > incomingDuration.toSeconds()) {
            return incomingDuration;
        }
        return saveDuration;
    }
}
