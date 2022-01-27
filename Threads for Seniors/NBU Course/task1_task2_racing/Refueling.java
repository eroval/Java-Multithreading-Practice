/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads.task1_task2_racing;

/**
 *
 * @author Kostadinova
 */
public class Refueling implements Runnable {

    private CarRacing racing;
    private double fuelNeeded;

    public Refueling(CarRacing racing, double fuelNeeded) {
        this.racing = racing;
        this.fuelNeeded = fuelNeeded;
    }

    public double getFuelNeeded() {
        return fuelNeeded;
    }

    @Override
    public void run() {
        boolean isFueled = racing.fueling(fuelNeeded);
        if (isFueled) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }

}
