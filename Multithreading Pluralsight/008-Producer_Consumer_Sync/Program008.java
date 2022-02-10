import javax.sound.midi.Sequencer.SyncMode;

class SharedSum{
    private final static Object syncObj = new Object();

    private static SharedSum obj = new SharedSum();
    private int Sum = 0;
    private SharedSum(){}
    private SharedSum(SharedSum obj){}

    public static SharedSum getSum(){
        // Synchronizing the singleton is not enough
        synchronized(syncObj){
            return obj;
        }
    }

    public static void incrementSum(){
        // If we were to delete the syncrhonization, 
        // annomalies would arise
        synchronized(syncObj){
            ++obj.Sum;
        }
    }

    public static void decrementSum(){
        synchronized(syncObj){
            --obj.Sum;
        }
    }

    @Override
    public String toString() {
        return "Sum = " + obj.Sum;
    }
}

class Producer{
    public void produce(){
        SharedSum.incrementSum();
    }
}

class Consumer{
    public void consume(){
        SharedSum.decrementSum();
    }
}

public class Program008 {
    // There is an obvious race condition. 
    // If we use high enough values the output will also not be done.
    // Which is why we use join in order to wait for all the threads to finish.

    private static int prodint=2000000;
    private static int consint=100000;


    private static void produce(){
        Producer p = new Producer();
        for(int i=0; i<prodint; i++) p.produce();
    }
    private static void consume(){
        Consumer c = new Consumer();
        for(int i=0; i<consint; i++) c.consume();
    }


    public static void main(String[] args) throws InterruptedException {

        Thread producerOne = new Thread(()->produce());
        Thread producerTwo = new Thread(()->produce());
        Thread consumerOne = new Thread(()->consume());

        producerOne.start();
        consumerOne.start();
        producerTwo.start();

        producerOne.join();
        consumerOne.join();
        producerTwo.join();

        System.out.println(SharedSum.getSum());
    }
}
