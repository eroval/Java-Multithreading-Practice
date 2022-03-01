import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SharedSum{
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    private static SharedSum obj = new SharedSum();
    private int Sum = 0;
    private SharedSum(){}
    private SharedSum(SharedSum obj){}

    public static SharedSum getSum(){
        try {
            obj.readLock.lock();
            return obj;
        }
        finally {
            obj.readLock.unlock();
        }
    }

    public static void incrementSum(){
        try {
            obj.writeLock.lock();
            ++obj.Sum;
        }
        finally {
            obj.writeLock.unlock();
        }
    }

    public static void decrementSum(){
        try {
            obj.writeLock.lock();
            --obj.Sum;
        }
        finally {
            obj.writeLock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Sum = " + obj.Sum;
    }
}

public class Program08 {

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads=5;
        ExecutorService producers= Executors.newFixedThreadPool(numberOfThreads);
        ExecutorService consumers= Executors.newFixedThreadPool(numberOfThreads);
        Runnable produce = () -> SharedSum.incrementSum();
        Runnable consume = () -> SharedSum.decrementSum();

        int multiplicator=2;
        int consumeTimes=10;
        int produceTimes=30;
        
        int consumeCounts = multiplicator*consumeTimes;
        int produceCounts = multiplicator*produceTimes;

        try{
            while(consumeCounts>0||produceCounts>0){
                if(new Random().nextInt()*1%2==1){
                    if(produceCounts>0){
                        producers.execute(produce);
                        --produceCounts;
                    }
                    else {
                    consumers.execute(consume);
                    --consumeCounts;
                    }
                }
                else {
                    if(consumeCounts>0){
                        consumers.execute(consume);
                        --consumeCounts;
                    }
                    else {
                        producers.execute(produce);
                        --produceCounts;
                    }
                }
            }
        }
        finally {
            consumers.shutdown();
            producers.shutdown();
        }
        while(!consumers.isTerminated()||!producers.isTerminated()){
            Thread.currentThread().sleep(100);
        }

        System.out.println(SharedSum.getSum());
    }
}
