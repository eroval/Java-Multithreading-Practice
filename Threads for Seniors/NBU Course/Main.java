package threads;

import threads.example1_scan.Scan;
import threads.example2_cinema.Projection;
import threads.example2_cinema.TicketsSelling;
import threads.task1_task2_racing.CarRacer;
import threads.task1_task2_racing.CarRacing;


import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        { // Week 12 Example 1 Scanning Documents

            Scan scan1 = new Scan("The Three Little Pigs", 5);
            Scan scan2 = new Scan("The Wolf and the Seven Little Goats", 5);

            Thread thread1 = new Thread(scan1, "Scan1");
            Thread thread2 = new Thread(scan2, "Scan2");

            System.out.println("Threads started!");
            thread1.setPriority(Thread.MAX_PRIORITY);
            thread2.setPriority(Thread.MIN_PRIORITY);

            thread1.start();
            thread2.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        { // Week 12 Example 2 Buying Tickets for a Movie example2_cinema.Projection

            Projection piratesOfTheCaribbean
                    = new Projection("Pirates of the Caribbean", 100);

            Thread john = new Thread(new TicketsSelling(piratesOfTheCaribbean, 60), "John");
            Thread marry = new Thread(new TicketsSelling(piratesOfTheCaribbean, 70), "Marry");
            john.start();
            marry.start();
        }

        { // Week 12 Example 3 example3_producer_consumer.Producer and example3_producer_consumer.Consumer, Interthread Communication: methods wait(), notify() and notifyAll()
//            example3_producer_consumer.Resource computer = new example3_producer_consumer.Resource();
//            example3_producer_consumer.Producer ibm = new example3_producer_consumer.Producer(computer);
//            example3_producer_consumer.Consumer stemo = new example3_producer_consumer.Consumer(computer);
//            Thread ibmThread = new Thread(ibm);
//            Thread stemoThread = new Thread(stemo);
//            ibmThread.start();
//            stemoThread.start();
        }

        { // Week 12 Task 1 Car Racing

            CarRacer ivan = new CarRacer("Ivan");
            CarRacer angel = new CarRacer("Angel");

            CarRacing carRacing = new CarRacing(20);
            carRacing.addRacer(angel);
            carRacing.addRacer(ivan);

            carRacing.race();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            carRacing.displayRankList();

        }

        { // Week 12 Task 2 Car Racing and Fueling

            CarRacer ivan = new CarRacer("Ivan");
            CarRacer angel = new CarRacer("Angel");

            CarRacing carRacing = new CarRacing(20, 100);
            carRacing.addRacer(angel);
            carRacing.addRacer(ivan);

            carRacing.startFueling(ivan, 80);
            carRacing.startFueling(angel, 40);


        }
    }
}
