

class TestThreads{
    private static class SumUpExample{
        long startRange;
        long endRange;
        long sumCounter = 0;
    
        public SumUpExample(long startRange, long endRange){
            this.startRange = startRange;
            this.endRange = endRange;
        }
    
        public void add(){
            for(long i = this.startRange; i<=this.endRange; i++){
                this.sumCounter+=i;
            }
        }
    };
    
    static final long MAX_NUM = Integer.MAX_VALUE;

    private static void twoThreads() throws InterruptedException{
        long start = System.currentTimeMillis();
        SumUpExample s1 = new SumUpExample(1, MAX_NUM/2);
        SumUpExample s2 = new SumUpExample(1+(MAX_NUM/2), MAX_NUM);

        Thread t1 = new Thread(()->{
            s1.add();
        });

        Thread t2 = new Thread(()->{
            s2.add();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long finalCount = s1.sumCounter + s2.sumCounter;
        long end = System.currentTimeMillis();
        System.out.println("Two threads final count = " + finalCount + " took " + (end - start));
    }

    private static void oneThread(){        
        long start = System.currentTimeMillis();
        SumUpExample s = new SumUpExample(1, MAX_NUM);
        s.add();
        long end = System.currentTimeMillis();
        System.out.println("One thread final count = " + s.sumCounter + " took " + (end - start));
    }

    public static void runTest() throws InterruptedException{
        twoThreads();
        oneThread();
    }
}

public final class P001Demonstration {
    public static void main(String[] args) throws InterruptedException{
        TestThreads.runTest();
    } 
}