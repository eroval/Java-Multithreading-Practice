package threads.example3_producer_consumer;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kostadinova
 */
public class Resource {

    private int quantity;
    private boolean valueSet = false;

    synchronized public int getQuantity() {
        while (!valueSet) {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println("Interruption!");
            }
        }
        System.out.println("Consumed quantity: " + quantity);
        valueSet = false;
        notify();
        return quantity;
    }

    public synchronized void setQuantity(int quantity) {
        while (valueSet) {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println("Interruption!");
            }
        }
        this.quantity = quantity;
        valueSet = true;
        System.out.println("Produced quantity: " + quantity);
        notify();
    }
}
