package java.util.concurrent;

public interface RunnableScheduledFuture<V> extends RunnableFuture<V>, ScheduledFuture<V> {
  boolean isPeriodic();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\concurrent\RunnableScheduledFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */