class  NonReentrantLock{
    boolean isLocked;

    public NonReentrantLock(){
        isLocked=false;
    }

    public synchronized void lock() throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked=true;
    }

    public synchronized void unlock() {
        isLocked=false;
        notify();
    }
}

public class P004Deadlock2 {
    public static void main(String args[]) throws Exception{
        NonReentrantLock nreLock = new NonReentrantLock();
        nreLock.lock();
        System.out.println("Acquired first lock.");
        nreLock.lock();
        //never will happen due to not being able to "unlock" the bool
        System.out.println("Acquired second lock.");
    }
}
