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
public class RaceThread implements Runnable {

    private CarRacing racing;
    private CarRacer carRacer;

    public RaceThread(CarRacing racing, CarRacer carRacer) {
        this.racing = racing;
        this.carRacer = carRacer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= racing.getNumberOfLaps(); i++) {
            System.out.println(this.carRacer.getName() + " tour: " + i);
            if (i == racing.getNumberOfLaps()) {
                System.out.println(this.carRacer.getName() + " finished the racing!");
                this.racing.addRacerToRankingList(carRacer);
            }
        }
    }
}
