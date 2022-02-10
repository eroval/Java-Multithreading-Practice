import javax.sound.midi.Sequencer.SyncMode;

// This will produce a deadlock
// because the race conditions depend on while clauses
// which makes this approach obsolete

class SharedSum{
    private final static Object syncObj = new Object();

    private static SharedSum obj = new SharedSum();
    private int Sum = 0;
    private SharedSum(){}
    private SharedSum(SharedSum obj){}

    public static SharedSum getSum(){
        synchronized(syncObj){
            return obj;
        }
    }

    public static void incrementSum(){
        synchronized(syncObj){
            ++obj.Sum;
        }
    }

    public static void decrementSum(){
        synchronized(syncObj){
            --obj.Sum;
        }
    }

    public int Value(){
        return this.Sum;
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

public class Program009 {
    private static void produce(){
        Producer p = new Producer();
        while(true) p.produce();
    }
    private static void consume(){
        Consumer c = new Consumer();
        while(SharedSum.getSum().Value()>0) c.consume();;
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
