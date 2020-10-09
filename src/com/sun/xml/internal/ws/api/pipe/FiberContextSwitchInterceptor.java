package com.sun.xml.internal.ws.api.pipe;

public interface FiberContextSwitchInterceptor {
  <R, P> R execute(Fiber paramFiber, P paramP, Work<R, P> paramWork);
  
  public static interface Work<R, P> {
    R execute(P param1P);
  }
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\pipe\FiberContextSwitchInterceptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */