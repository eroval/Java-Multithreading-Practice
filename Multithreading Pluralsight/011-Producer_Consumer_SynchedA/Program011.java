import javax.sound.midi.Sequencer.SyncMode;

class SharedSum{
    private final static Object syncObj = new Object();

    private static SharedSum obj = new SharedSum();
    private int Sum = 0;

    // check when buffer is full
    private int maxVal = 2000;
    private SharedSum(){}
    private SharedSum(SharedSum obj){}

    public static SharedSum getSum(){
        synchronized(syncObj){
            return obj;
        }
    }

    public static void incrementSum() throws InterruptedException{
        synchronized(syncObj){
            if(obj.Sum==obj.maxVal){
                syncObj.wait();
            }
            ++obj.Sum;
            syncObj.notifyAll();
        }
    }

    public static void decrementSum() throws InterruptedException{
        synchronized(syncObj){
            if(obj.Sum==0){
                syncObj.wait();
            }
            --obj.Sum;
            syncObj.notifyAll();
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
    public void produce() throws InterruptedException{
        SharedSum.incrementSum();
    }
}

class Consumer{
    public void consume() throws InterruptedException{
        SharedSum.decrementSum();
    }
}

public class Program011 {
    private static void produce() {
        Producer p = new Producer();
        while(true) {
            System.out.println(SharedSum.getSum().Value());
            try {
                p.produce();
            } catch (InterruptedException e) { }
        }
    }
    private static void consume() {
        Consumer c = new Consumer();
        while(true) {
            System.out.println(SharedSum.getSum().Value());
            try {
                c.consume();
            } catch (InterruptedException e) { }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        Thread producerOne = new Thread(()-> produce());
        Thread consumerOne = new Thread(()-> consume());

        producerOne.start();
        consumerOne.start();

        producerOne.join();
        consumerOne.join();

        System.out.println(SharedSum.getSum());
    }
}
