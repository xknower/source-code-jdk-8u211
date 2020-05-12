package java.util.concurrent;

public interface Delayed extends Comparable<Delayed> {
  long getDelay(TimeUnit paramTimeUnit);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\concurrent\Delayed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */