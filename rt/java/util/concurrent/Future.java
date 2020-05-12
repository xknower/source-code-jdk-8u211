package java.util.concurrent;

public interface Future<V> {
  boolean cancel(boolean paramBoolean);
  
  boolean isCancelled();
  
  boolean isDone();
  
  V get() throws InterruptedException, ExecutionException;
  
  V get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\concurrent\Future.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */