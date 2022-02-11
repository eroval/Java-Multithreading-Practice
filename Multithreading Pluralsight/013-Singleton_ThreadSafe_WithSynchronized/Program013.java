class Singleton{
    private static Singleton instance;
    private String name = "Denis";
    private int age = 0;

    private Singleton(){}
    private Singleton(Singleton singleton){}
    
    //locking objects
    private final Object keyName = new Object();
    private final Object keyAge = new Object();

    public static synchronized Singleton get(){
        if(instance==null){
            instance = new Singleton();
        }
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


public class Program013{
    public static void main(String[] args) throws InterruptedException {
        Runnable a = () -> { while(!Thread.currentThread().interrupted()) System.out.println(Singleton.get().getAge()); };
        Runnable b = () -> { while(!Thread.currentThread().interrupted()) System.out.println(Singleton.get().getAge()); };

        int n = 50;
        Thread[] threads = new Thread[n];
        for(int i=0; i<n; i++){
            if(i%2==0){
                threads[i] = new Thread(a);
            }
            else threads[i] = new Thread(b);
        }



        for(int i=0; i<n; i++) threads[i].start();

        Singleton.get().setAge(20);
        Singleton.get().setName("Lano");


        for(int i=0; i<n; i++) {
            threads[i].interrupt();
            threads[i].join();
        }
    }
}