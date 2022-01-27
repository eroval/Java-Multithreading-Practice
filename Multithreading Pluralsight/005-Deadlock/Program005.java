class Cool{
    //locking objects
    private final Object keyA = new Object();
    private final Object keyB = new Object();

    public void functionA(){
        synchronized(keyA){
            System.out.println("I'm in function A.");
            functionB();
        }
    }

    public void functionB(){
        synchronized(keyB){
            System.out.println("I'm in function B.");
            functionC();
        }
    }

    public void functionC(){
        synchronized(keyA){
            System.out.println("I'm in function C.");
        }
    }
}


public class Program005{
    public static void main(String[] args) throws InterruptedException {
        Cool myobj = new Cool();
        Runnable a = () -> myobj.functionA();
        Runnable b = () -> myobj.functionB();

        Thread t1 = new Thread(a);
        Thread t2 = new Thread(b);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}