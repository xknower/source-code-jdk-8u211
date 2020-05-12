package com.sun.corba.se.spi.orbutil.threadpool;

public interface ThreadPoolChooser {
  ThreadPool getThreadPool();
  
  ThreadPool getThreadPool(int paramInt);
  
  String[] getThreadPoolIds();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\orbutil\threadpool\ThreadPoolChooser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */