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

    // fair lock
    private static Lock lock = new ReentrantLock(true);


    private ImportantValue(){

    }

    private ImportantValue(ImportantValue obj){

    }

    public static ImportantValue getInstance(){
        try{
            lock.lock();
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

    public double getValue(){
        try{
            lock.lock();
            return instance.value;
        } 
        finally{
            lock.unlock();
            System.out.println("Unlocking the object.");
        }
    }

    public void changeValue(){
        try{
            lock.lock();
            int minRange = 100;
            int maxRange = 3000;
            instance.value = minRange + (maxRange-minRange) * new Random().nextDouble();
        }
        finally {
            lock.unlock();
        }
    }
};

public class Program06 {
    public static void main(String[] args) {
        int numberOfThreads=10;
        ExecutorService threads = Executors.newFixedThreadPool(numberOfThreads);
        Callable<Double> task = () -> ImportantValue.getInstance().getValue();
        Runnable taskTwo = () -> ImportantValue.getInstance().changeValue();

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
