package com.sun.corba.se.spi.orbutil.fsm;

public interface FSM {
  State getState();
  
  void doIt(Input paramInput);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\orbutil\fsm\FSM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */