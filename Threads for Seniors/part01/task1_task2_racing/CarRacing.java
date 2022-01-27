/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads.task1_task2_racing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kostadinova
 */
public class CarRacing {

    private int numberOfLaps;
    private List<CarRacer> racers;
    private List<CarRacer> rankingList;
    private double fuelQuantity;

    public CarRacing(int numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
        racers = new ArrayList();
        rankingList = new ArrayList();
    }

    public CarRacing(int numberOfLaps, double fuelQuantity) {
        racers = new ArrayList();
        this.numberOfLaps = numberOfLaps;
        this.fuelQuantity = fuelQuantity;
        rankingList = new ArrayList();

    }

    public void addRacer(CarRacer carRacer) {
        racers.add(carRacer);
    }

    public void race() {
        for (CarRacer racer : this.racers) {
            Thread thread = new Thread(new RaceThread(this, racer));
            thread.start();
        }
    }

    public void addRacerToRankingList(CarRacer carRacer) {
        rankingList.add(carRacer);
    }

    public void displayRankList() {
        System.out.println("Rank List: ");
        for (CarRacer racer : this.rankingList) {
            System.out.println(racer);
        }
    }

    public synchronized boolean fueling(double quantityNeeded) {
        System.out.println(Thread.currentThread().getName() + " " +
                quantityNeeded + " " + this.getFuelCurrentQuantity());
        if (this.getFuelCurrentQuantity() >= quantityNeeded) {
            this.fuelQuantity -= quantityNeeded;
            return true;
        }
        return false;
    }


    public void startFueling(CarRacer carRacer, final double fuelNeeded) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean isFueled = fueling(fuelNeeded);
                if (isFueled) {
                    System.out.println("True");
                } else {
                    System.out.println("False");
                }
            }
        };
        new Thread(runnable, carRacer.getName()).start();

    }

    private double getFuelCurrentQuantity() {
        return this.fuelQuantity;
    }

    int getNumberOfLaps() {
        return this.numberOfLaps;
    }

}
