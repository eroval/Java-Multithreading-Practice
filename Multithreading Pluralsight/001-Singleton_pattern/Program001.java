

class Singleton{
    private static Singleton instance = new Singleton();
    private int age = 0;

    private Singleton(){}
    private Singleton(Singleton singleton){}

    // synchronized is used for threads and
    // has nothing to do with the singleton pattern itself
    // it generates a key for accessing the object
    // in this key the object which holds the key is the object itself
    // but it can be changed to explicitly relatively simple
    
    // private static final Object key = new Object();
    // public static Singleton get(){
    //     synchronized(key){
    //         return instance;
    //     }  
    // }

    public static synchronized Singleton get(){
        return instance;
    }

    public synchronized void setAge(int age){
        this.age=age;
    }

    @Override
    public String toString() {
        return "Age = " + this.age; 
    }
}


public class Program001{
    public static void main(String[] args) {
        System.out.println(Singleton.get());

        // making the copy constructor private
        // tricks java into forbiding the creation 
        // of a copy, so that only a reference is possible

        // try
        // Singleton a = new Singleton(Singleton.get());
        Singleton a = Singleton.get();
        a.setAge(5);
        System.out.println(a.get());
        System.out.println(Singleton.get());

        // Another check through the get method
        Singleton.get().setAge(20);
        System.out.println(Singleton.get());
        System.out.println(a.get());
    }
}