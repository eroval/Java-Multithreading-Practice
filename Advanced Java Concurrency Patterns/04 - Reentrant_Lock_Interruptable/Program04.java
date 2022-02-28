import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ImportantValue{
    private static ImportantValue instance = new ImportantValue();
    private double value=3.14;
    private static Lock lock = new ReentrantLock();


    private ImportantValue(){

    }

    private ImportantValue(ImportantValue obj){

    }

    public static ImportantValue getInstance() throws InterruptedException{
        try{
            // Lock can be interrupted by a different
            // thread by calling interrupt
            lock.lockInterruptibly();
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("Locking the object");
            System.out.println("Returning the instance");
            return instance;
        }
        finally{
            lock.unlock();
            System.out.println("Unlocking the object.");
        }
    }

    public double getValue() throws InterruptedException{
        try{
            lock.lockInterruptibly();
            return instance.value;
        } 
        finally{
            lock.unlock();
            System.out.println("Unlocking the object.");
        }
    }

    public void changeValue() throws InterruptedException{
        try{
            lock.lockInterruptibly();
            int minRange = 100;
            int maxRange = 3000;
            instance.value = minRange + (maxRange-minRange) * new Random().nextDouble();
        }
        finally {
            lock.unlock();
        }
    }
};

public class Program04 {
    public static void main(String[] args) {
        int numberOfThreads=10;
        ExecutorService threads = Executors.newFixedThreadPool(numberOfThreads);
        Callable<Double> task = () -> ImportantValue.getInstance().getValue();
        Runnable taskTwo = () -> {
            try {
                ImportantValue.getInstance().changeValue();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        };

        try{
            for(int i=0; i<numberOfThreads*20; i++){
                if(new Random().nextInt()*1%2==1){
                    System.out.println("Changing value.");
                    threads.execute(taskTwo);
                }
                Future<Double> value = threads.submit(task);
                System.out.println("Value is equal to " + value.get() + "\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        finally{
            threads.shutdown();
        }
    }
}
