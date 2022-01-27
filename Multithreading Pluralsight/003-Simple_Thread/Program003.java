public class Program003 {
    public static void main(String[] args) {
        Runnable runnable = () ->{
            System.out.println("Thread " + Thread.currentThread().getName() + " is running.");
        };
        
        Thread t = new Thread(runnable);
        t.setName("Cool");
        t.start();
    }
}
