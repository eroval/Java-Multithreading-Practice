package threads.example3_producer_consumer;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kostadinova
 */
public class Consumer implements Runnable {

    Resource resource;
    Thread thread;

    public Consumer(Resource resource) {
        this.resource = resource;
        this.thread = new Thread(this, "example3_producer_consumer.Consumer");
    }

    @Override
    public void run() {
        while (true) {
            this.resource.getQuantity();
        }
    }
}
