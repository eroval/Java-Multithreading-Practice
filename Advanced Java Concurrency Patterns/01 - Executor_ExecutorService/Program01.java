import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Program01{
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> System.out.println("Running task in " + Thread.currentThread().getName());
        ExecutorService service = Executors.newSingleThreadExecutor();

        int length=100;
        
        for(int i=0; i<length; i++){
            service.execute(task);
        } 
        service.shutdown();

        Thread.currentThread().sleep(1000);
        
        int n = 5;
        System.out.println("\n\nFixed pool of " + n + " threads.\n\n");
        ExecutorService service2 = Executors.newFixedThreadPool(n);

        for(int i=0; i<length; i++){
            service2.execute(task);
        }
        service2.shutdown();
    }
}