public class LongWrapper {
    private long l;
    private Object key = new Object();

    public LongWrapper(long l){
        this.l=l;
    }

    public long getValue(){
        synchronized(key){
            return this.l;
        }
    }

    public void incrementValue(){
        synchronized(key){
            ++this.l;
        }
    }
}
