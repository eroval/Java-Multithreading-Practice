import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import javax.management.InvalidAttributeValueException;
import javax.naming.directory.AttributeModificationException;

class Person {
    // Threads & Their Functions
    private static int threadCount=4;
    private static ExecutorService threads = Executors.newFixedThreadPool(threadCount);
    private static Semaphore semaphoreWrite= new Semaphore(threadCount/2+threadCount%2);
    private static Semaphore semaphoreRead = new Semaphore(threadCount/2+threadCount%2);

    private void changeThreadCount(int threadCount){
        threads.shutdown();
        this.threads = Executors.newFixedThreadPool(threadCount);
        this.semaphoreWrite = new Semaphore(threadCount/2+threadCount%2);
        this.semaphoreRead = new Semaphore(threadCount/2+threadCount%2);
    }

    public void setThreadCount(int threadCount){
        try{
            if(threadCount<=0) throw new InvalidAttributeValueException("Cannot set number of threads to a nonpositive number.");
            this.changeThreadCount(threadCount);
            this.threadCount=threadCount;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private String name;
    private int age;
    private List<Person> relatives = new ArrayList<Person>();


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


    private void asyncAddRelatives(int k, Person... person) throws InterruptedException{
        try{
            semaphoreWrite.acquire();
            for(; k<person.length;k+=threadCount){
                this.relatives.add(person[k]);
            }
        }
        finally{
            semaphoreWrite.release();
        }
    }

    private void asyncAddRelatives(int k, ArrayList<Person> person) throws InterruptedException{
        try{
            semaphoreWrite.acquire();
            for(; k<person.size();k+=threadCount){
                this.relatives.add(person.get(k));
            }
        }
        finally{
            semaphoreWrite.release();
        }
    }

    // Functions:
    public void addRelatives(Person... person) throws InterruptedException{
        for(int i=0; i<threadCount; i++){
            final int k = i;
            threads.execute(new Runnable(){
                public void run(){
                    try {
                        asyncAddRelatives(k, person);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void addRelatives(ArrayList<Person> person) throws InterruptedException{
        for(int i=0; i<threadCount; i++){
            final int k = i;
            threads.execute(new Runnable(){
                public void run(){
                    try {
                        asyncAddRelatives(k, person);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public List<Person> getRelatives() throws InterruptedException{
        try {
            semaphoreRead.acquire();
            return this.relatives;
        }
        finally{
            semaphoreRead.release();
        }
    }

    private String getInformation() throws InterruptedException{
        String person = "Person{\n" + "\tname:" + this.name+",\n\tage="+this.age+",\n\t";
        String relativesString = "relatives:\n\t{\n\t\t";
        try{
            semaphoreRead.acquire();
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
            semaphoreRead.release();
        }
    }

    public String getAllInformation() throws InterruptedException, ExecutionException{
        Callable<String> task = () -> getInformation();
        Future<String> info = this.threads.submit(task);
        return info.get();
    }

    public void stop(){
        this.threads.shutdown();
    }

    // Stringify
    @Override
    public String toString() {
        return "Person{\n" + "\tname:" + this.name+",\n\tage="+this.age+"\n}\n";
    }
}

public class Program10 {
    public static void main(String[] args) throws InvalidAttributeValueException {
            Person pOne = new Person("Harry", 20);
            
            Person pTwo = new Person("Garry", 30);
            Person pThree = new Person("Larry", 30);
            Person pFour = new Person("Barry", 30);
            Person pFive = new Person("Denis", 5);
            Person pSix = new Person("Delian", 4);
            Person pSeven = new Person("Atanas", 4);

            ArrayList<Person> peopleToAdd = new ArrayList<>();
            peopleToAdd.add(pTwo);
            peopleToAdd.add(pThree);
            peopleToAdd.add(pFour);
            peopleToAdd.add(pFive);
            peopleToAdd.add(pSix);
            peopleToAdd.add(pSeven);

            ArrayList<Person> people = new ArrayList<>();
            for(int i=0; i<100; i++){
                people.addAll(peopleToAdd);
            }   

            try {
                pOne.addRelatives(people);
                System.out.println(pOne.getAllInformation());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch blockf
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                pOne.stop();
            }
    }
}
