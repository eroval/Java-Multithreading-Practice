

import java.util.Random;

class RaceCondition{
    int randInt;
    int counter = 10000;
    Random random = new Random(System.currentTimeMillis());

    void printer(){
        int i=counter;
        while(i!=0){
            synchronized(this){
                if(randInt%5==0) System.out.println(randInt);
            }
            i--;
        }
    }

    void modifier(){
        int i=counter;
        while(i!=0){
            synchronized(this){
                randInt = random.nextInt(1000);
                i--;
            }
        }
    }

    public static void runTest() throws InterruptedException{
        final RaceCondition rc = new RaceCondition();
        Thread t1 = new Thread(new Runnable(){

            public void run(){
                rc.printer();
            }
        });
        Thread t2 = new Thread(new Runnable(){

            public void run(){
                rc.modifier();;
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

class P0003RaceCondition {
    public static void main(String args[]) throws InterruptedException{
        RaceCondition.runTest();
    }
}