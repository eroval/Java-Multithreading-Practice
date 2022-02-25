import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Program02 {
    public static void main(String[] args){
        
        Callable<String> task = () -> {
            return "I am now returning task running in " + Thread.currentThread().getName(); 
        };

        int length = 10;
        int n = 8;
        ExecutorService service = Executors.newFixedThreadPool(n);

        try {
            for(int i=0; i<length; i++){
                Future<String> future = service.submit(task);
                System.out.println("The result is " + future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        finally{
        service.shutdown();
        }
    }
}
