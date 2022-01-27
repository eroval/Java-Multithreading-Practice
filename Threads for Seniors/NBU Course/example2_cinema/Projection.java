package threads.example2_cinema;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kostadinova
 */
public class Projection {

    private String name;
    private int maxNumberOfSeats;
    private int soldTickets;

    public Projection() {
    }

    public Projection(String name, int maxNumberOfSeats) {
        this.name = name;
        this.maxNumberOfSeats = maxNumberOfSeats;
    }

    public String getName() {
        return name;
    }

    public int getMaxNumberOfSeats() {
        return maxNumberOfSeats;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    int getAvailableTickets() {
        return this.maxNumberOfSeats - this.soldTickets;
    }

    void setTicketsSold(int soldTickets) {
        this.soldTickets += soldTickets;
    }

    public synchronized boolean sellTickets(int ticketsNeeded) {
        System.out.println(Thread.currentThread().getName() + " " +
                ticketsNeeded + " " + this.getAvailableTickets());
        if (this.getAvailableTickets() >= ticketsNeeded) {
            this.setTicketsSold(ticketsNeeded);
            return true;
        } else {
            return false;
        }
    }
}
