package java.util.concurrent;

public interface RunnableFuture<V> extends Runnable, Future<V> {
  void run();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\concurrent\RunnableFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */