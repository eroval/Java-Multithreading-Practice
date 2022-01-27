class Singleton{
    private static Singleton instance = new Singleton();
    private String name = "Denis";
    private int age = 0;

    private Singleton(){}
    private Singleton(Singleton singleton){}
    
    //locking objects
    private final Object keyName = new Object();
    private final Object keyAge = new Object();

    //there will be no locking synchronization in the get method now
    public static Singleton get(){
        return instance;
    }

    public void setAge(int age){
        synchronized(this.keyAge){
            this.age=age;
        }
    }

    public int getAge(){
        synchronized(this.keyAge){
            return this.age;
        }
    }

    public void setName(String name){
        synchronized(this.keyName){
            this.name=name;
        }
    }

    public String getName(){
        synchronized(this.keyName){
            return this.name;
        }
    }

    @Override
    public String toString() {
        return "Name = " + this.name + "\nAge = " + this.age; 
    }
}


public class Program004{
    public static void main(String[] args) throws InterruptedException {
        Runnable a = () -> System.out.println(Singleton.get().getAge());
        Runnable b = () -> System.out.println(Singleton.get().getName());

        Thread t1 = new Thread(a);
        Thread t2 = new Thread(b);

        t1.start();
        t2.start();
        // althought we expect the threads to return 0, often times they take 
        // time in order to start and the main thread would be the overtaker
        // add
        // Thread.sleep(200);
        // in order to make sure that they start before the main thread continues execution and they lock the object
        Singleton.get().setAge(20);
        Singleton.get().setName("Lano");

        t1.join();
        t2.join();
    }
}