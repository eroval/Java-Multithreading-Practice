import java.io.IOException;

class Cool{
    private final Object keyA = new Object();

    public void functionA() throws InterruptedException{
        while(!Thread.currentThread().isInterrupted()){
            synchronized(keyA){
                System.out.println("I'm in function A.");
            }
        }
    }

    private void functionC() throws InterruptedException{
        while(!Thread.currentThread().isInterrupted()){
            Thread.sleep(5000);

            synchronized(keyA){
                System.out.println("Press Enter to stop the threads.");
                Thread.sleep(4000);
            }
        }
    }

    public void functionB(Thread threadOne) throws InterruptedException{
        Runnable c = () -> {
            try {
                functionC();
            } 
            catch (InterruptedException e1) {}
        };
        
        Thread outputThread = new Thread(c);
        outputThread.start();

        try{
            System.in.read();
            threadOne.interrupt();
            outputThread.interrupt();
        }

        catch(Exception e){}
        outputThread.join();
    }
}


public class Program006 {
    
    public static void main(String[] args) throws InterruptedException {
        Cool myobj = new Cool();

        // Thread t1
        Runnable a = () -> {
            try {
                myobj.functionA();
            } catch (InterruptedException e1) {}
        };

        Thread t1 = new Thread(a);



        // Thread t2
        Runnable b = () -> {
                try {
                    myobj.functionB(t1);
                } catch (InterruptedException e) {}
        };

        Thread t2 = new Thread(b);
        
        t1.start();
        t2.start();


        t1.join();
        t2.join();
    }
}
