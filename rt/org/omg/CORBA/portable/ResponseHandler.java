package org.omg.CORBA.portable;

public interface ResponseHandler {
  OutputStream createReply();
  
  OutputStream createExceptionReply();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\portable\ResponseHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */