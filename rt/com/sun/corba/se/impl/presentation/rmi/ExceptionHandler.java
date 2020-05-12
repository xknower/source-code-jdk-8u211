package com.sun.corba.se.impl.presentation.rmi;

import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA_2_3.portable.OutputStream;

public interface ExceptionHandler {
  boolean isDeclaredException(Class paramClass);
  
  void writeException(OutputStream paramOutputStream, Exception paramException);
  
  Exception readException(ApplicationException paramApplicationException);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\presentation\rmi\ExceptionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */