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
    private final String from ;
    private final String to;

    public CalculationService(List<Ticket> ticketList, String from, String to) {
        this.from = from;
        this.to = to;
        this.ticketList = ticketList;
    }

    public void calculateMedianDifferenceAveragePrice() {
        if (ticketList.isEmpty()) {
            return;
        }

        List<Ticket> sortedTicketList = ticketList.stream()
                .filter(this::isaRightRoute)
                .sorted(Comparator.comparing(Ticket::getPrice))
                .collect(Collectors.toList());
        int result = getMedineAverage(sortedTicketList);

        System.out.println(String.format("Разница медины и средней цены из %s в %s составляет %d", from, to, result));
    }

    private boolean isaRightRoute(Ticket ticket) {
        return ticket.getOrigin().equals(from) && ticket.getDestination().equals(to);
    }


    private int getMedineAverage(List<Ticket> sortTickets) {
        int medinePrice = getMedianPrice(sortTickets);
        int averagePrice = getAveragePrice(sortTickets);
        return medinePrice - averagePrice;
    }

    private static int getAveragePrice(List<Ticket> sortTickets) {
        AtomicInteger sum = new AtomicInteger();
        sortTickets.forEach(ticket -> sum.addAndGet(ticket.getPrice()));
        int countTickets = sortTickets.size();
        return sum.get() / countTickets;
    }

    private static int getMedianPrice(List<Ticket> sortTickets) {
        int averageNumber = 2;
        if (sortTickets.size() % 2 == 0) {
            int oneMedianNumb = (sortTickets.size() - 1) / averageNumber;
            int twoMedianNumb = oneMedianNumb + 1;
            return (sortTickets.get(oneMedianNumb).getPrice() + sortTickets.get(twoMedianNumb).getPrice()) / 2;
        }
        return sortTickets.get(((sortTickets.size() - 1) / averageNumber)).getPrice();
    }

    public void calculateMinimalTimeFlight() {
        Map<String, Duration> shortTimeArrived = new HashMap<>();
        if (ticketList.isEmpty()) {
            return;
        }
        ticketList.stream().filter(this::isaRightRoute)
                .peek(ticket -> shortTimeArrived.putIfAbsent(ticket.getCarrier(), getDurationTicket(ticket)))
                .forEach(ticket -> shortTimeArrived.put(ticket.getCarrier(), getShortDuration(getDurationTicket(ticket), shortTimeArrived.get(ticket.getCarrier()))));

        shortTimeArrived.keySet().forEach(key -> printResult(key, shortTimeArrived));
    }

    private void printResult(String carrier, Map<String, Duration> shortTimeArrived) {
        Duration duration = shortTimeArrived.get(carrier);
        System.out.println(String.format("Продолжительность самого короткого поерелёта из %s в %s перевозчика %s составила %d часов %d минут", from, to, carrier, duration.toHoursPart(), duration.toMinutesPart()));
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
