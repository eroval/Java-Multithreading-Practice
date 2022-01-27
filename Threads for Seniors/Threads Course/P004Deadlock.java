

class Deadlock{
    private int counter=0;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    void incrementCounter() throws InterruptedException{
        synchronized(lock1){
            System.out.println("Acquired lock1");
            Thread.sleep(100);

            synchronized(lock2){
                counter++;
            }
        }
    }

    void decrementCounter() throws InterruptedException{
        synchronized(lock2){
            System.out.println("Acquired lock2");

            Thread.sleep(100);
            synchronized(lock1){
                counter--;
            }
        }
    }
    

    Runnable incrementer = new Runnable(){

        @Override
        public void run(){
            try{
                for(int i=0; i<100; i++){
                    incrementCounter();
                    System.out.println("Incrementing " + i);
                }
            }
            catch(InterruptedException ie){

            }
        }

    };

    Runnable decrementer = new Runnable(){

        @Override
        public void run(){
            try{
                for(int i=0; i<100; i++){
                    decrementCounter();
                    System.out.println("Decrementing " + i);
                }
            }
            catch(InterruptedException ie){

            }
        }

    };

    public void runTest() throws InterruptedException{
        Thread t1 = new Thread(incrementer);
        Thread t2 = new Thread(decrementer);

        t1.start();
        Thread.sleep(100);
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Done: "+counter);
    }
}



class P004Deadlock {
    
    
    public static void main(String args[]){
        Deadlock deadlock = new Deadlock();
        try{
            deadlock.runTest();
        }
        catch (InterruptedException ie){

        }
    }
}