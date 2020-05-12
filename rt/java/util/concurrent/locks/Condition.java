package java.util.concurrent.locks;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface Condition {
  void await() throws InterruptedException;
  
  void awaitUninterruptibly();
  
  long awaitNanos(long paramLong) throws InterruptedException;
  
  boolean await(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  boolean awaitUntil(Date paramDate) throws InterruptedException;
  
  void signal();
  
  void signalAll();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\concurrent\locks\Condition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */