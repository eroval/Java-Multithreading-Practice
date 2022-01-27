package threads.example3_producer_consumer;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kostadinova
 */
public class Producer implements Runnable {
    
    Resource resource;
    Thread thread;
    
    public Producer(Resource resource) {
        this.resource = resource;
        this.thread = new Thread(this, "example3_producer_consumer.Producer");
    }
    
    @Override
    public void run() {
        int sourceCounter = 0;
        while (true) {
            this.resource.setQuantity(sourceCounter++);
        }
    }
    
}
