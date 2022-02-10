class SharedSum{
    private static SharedSum obj = new SharedSum();
    private int Sum = 0;
    private SharedSum(){}
    private SharedSum(SharedSum obj){}

    public static SharedSum getSum(){
        return obj;
    }

    public static void incrementSum(){
        ++obj.Sum;
    }

    public static void decrementSum(){
        --obj.Sum;
    }

    @Override
    public String toString() {
        return "Sum = " + obj.Sum;
    }
}

class Producer{
    public void produce(){
        SharedSum.incrementSum();
    }
}

class Consumer{
    public void consume(){
        SharedSum.decrementSum();
    }
}

public class Program007 {
    public static void main(String[] args) {
        Producer producerOne = new Producer();
        Producer producerTwo = new Producer();

        Consumer consumerOne = new Consumer();

        for(int i=0; i<100; i++){
            producerOne.produce();
            producerTwo.produce();
        }

        consumerOne.consume();

        System.out.println(SharedSum.getSum());
    }
}
