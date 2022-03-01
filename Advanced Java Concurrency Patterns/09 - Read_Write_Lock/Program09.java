import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.management.InvalidAttributeValueException;

class Person {
    private String name;
    private int age;
    private List<Person> relatives = new ArrayList<Person>();

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();


    // Constructors
    public Person(String name, int age) throws InvalidAttributeValueException{
        this.setName(name);
        this.setAge(age);
    }
    
    public Person(int age, String name) throws InvalidAttributeValueException{
        this.setName(name);
        this.setAge(age);
    }


    // Setters & Getters
    private void setName(String name) throws InvalidAttributeValueException{
        if(name.isBlank()||name.isEmpty()){
            throw new InvalidAttributeValueException("Name cannot be blank!");
        }
        this.name= name;
    }

    private void setAge(int age) throws InvalidAttributeValueException{
        if(age<0||age>120){
            throw new InvalidAttributeValueException("Age cannot be smaller than 0.");
        }
        this.age=age;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }


    // Functions:
    public void addRelatives(Person... person){
        try{
            writeLock.lock();
            for(Person p: person){
                this.relatives.add(p);
            }
        }
        finally{
            writeLock.unlock();
        }
    }

    public void addRelatives(ArrayList<Person> relatives){
        try{
            writeLock.lock();
            this.relatives.addAll(relatives);
        }
        finally{
            writeLock.unlock();
        }
    }

    public List<Person> getRelatives(){
        try {
            readLock.lock();
            return this.relatives;
        }
        finally{
            readLock.unlock();
        }
    }

    public String getAllInformation(){
        String person = "Person{\n" + "\tname:" + this.name+",\n\tage="+this.age+",\n\t";
        String relativesString = "relatives:\n\t{\n\t\t";
        try{
            readLock.lock();
            Person p = this.relatives.get(0);
            relativesString+="Person_0" +"{\n\t\tname:"+p.name+",\n\t\tage:"+p.age+"\n\t\t}";
            for(int i=1; i<relatives.size(); i++){
                relativesString+=",\n\n\t\t";
                relativesString+="Person_"+i+"{\n\t\tname:"+this.relatives.get(i).name+",\n\t\tage:"+this.relatives.get(i).age+"\n\t\t}";
            }
            relativesString += "\n\t}\n}";
            return person + relativesString;
        }
        finally{
            readLock.unlock();
        }
    }

    // Stringify
    @Override
    public String toString() {
        return "Person{\n" + "\tname:" + this.name+",\n\tage="+this.age+"\n}\n";
    }
}

public class Program09 {
    public static void main(String[] args) throws InvalidAttributeValueException {
            Person pOne = new Person("Harry", 20);
            Runnable taskWriteOne = () -> {
                try{
                    Person pTwo = new Person("Garry", 30);
                    Person pThree = new Person("Larry", 30);
                    Person pFour = new Person("Barry", 30);
                    pOne.addRelatives(pTwo, pThree, pFour);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            };

            Runnable taskWriteTwo = () -> {
                try{
                    Person pTwo = new Person("Denis", 5);
                    Person pThree = new Person("Delian", 4);
                    Person pFour = new Person("Atanas", 4);
                    pOne.addRelatives(pTwo, pThree, pFour);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            };
            
            Runnable taskRead = () -> {
                System.out.println(pOne.getAllInformation());
            };
            
            ExecutorService threads = Executors.newFixedThreadPool(4);
            
            try{
                for(int i=0; i<100; i++){
                    threads.execute(taskWriteOne);
                    threads.execute(taskWriteTwo);
                }   

                threads.execute(taskRead);
            }
            finally{
                threads.shutdown();
            }
    }
}
