package threads.example2_cinema;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kostadinova
 */
public class TicketsSelling implements Runnable {

    private Projection projection;
    private int ticketsNeeded;

    public TicketsSelling(Projection projection, int ticketsNeeded) {
        this.projection = projection;
        this.ticketsNeeded = ticketsNeeded;
    }

    public Projection getProjection() {
        return projection;
    }

    public int getTicketsNeeded() {
        return ticketsNeeded;
    }

    @Override
    public void run() {
        boolean isSold = projection.sellTickets(ticketsNeeded);
        if (isSold) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }

}
