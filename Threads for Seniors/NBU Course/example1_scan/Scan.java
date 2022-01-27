package threads.example1_scan;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * @author Kostadinova
 */
public class Scan implements Runnable {

    private String documentName;
    private int numberOfTimesToScan;

    public Scan(String documentName, int numberOfTimesToScan) {
        this.documentName = documentName;
        this.numberOfTimesToScan = numberOfTimesToScan;
    }

    @Override
    public void run() {
        for (int i = 1; i <= numberOfTimesToScan; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + "priority: " + Thread.currentThread().getPriority() + " "
                    + documentName + " scanned " + i + " number of times.");
        }
    }

}
