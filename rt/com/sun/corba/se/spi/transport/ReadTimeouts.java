package com.sun.corba.se.spi.transport;

public interface ReadTimeouts {
  int get_initial_time_to_wait();
  
  int get_max_time_to_wait();
  
  double get_backoff_factor();
  
  int get_max_giop_header_time_to_wait();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\transport\ReadTimeouts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */