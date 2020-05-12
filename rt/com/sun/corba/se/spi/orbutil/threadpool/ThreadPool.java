package com.sun.corba.se.spi.orbutil.threadpool;

import java.io.Closeable;

public interface ThreadPool extends Closeable {
  WorkQueue getAnyWorkQueue();
  
  WorkQueue getWorkQueue(int paramInt) throws NoSuchWorkQueueException;
  
  int numberOfWorkQueues();
  
  int minimumNumberOfThreads();
  
  int maximumNumberOfThreads();
  
  long idleTimeoutForThreads();
  
  int currentNumberOfThreads();
  
  int numberOfAvailableThreads();
  
  int numberOfBusyThreads();
  
  long currentProcessedCount();
  
  long averageWorkCompletionTime();
  
  String getName();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\orbutil\threadpool\ThreadPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */