// Using two objects for synchronization on a static class
// gives us the advantage of accessing different fields in the class
// but still locking these specific fields for the threads

// As an example:
// Thread one could be getting and setting the name
// And thread two could be getting and setting the age
// In the case of a static singleton this gives us an advantage in multithreading

// If the class wasn't a singleton and we wanted to lock all the methods for all instances
// the locking objects themselves can be static objects

// However, if we wanted to have every object for the instances, they could be left as they are


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


public class Program002{
    public static void main(String[] args) {
        Singleton a = Singleton.get();
        System.out.println(a.get());
        a.setAge(5);
        System.out.println(a.get());
        System.out.println(Singleton.get());

        // Another check through the get method
        Singleton.get().setAge(20);
        System.out.println(Singleton.get());
        System.out.println(a.get());
    }
}