package javax.xml.ws;

import java.util.concurrent.Future;

public interface Dispatch<T> extends BindingProvider {
  T invoke(T paramT);
  
  Response<T> invokeAsync(T paramT);
  
  Future<?> invokeAsync(T paramT, AsyncHandler<T> paramAsyncHandler);
  
  void invokeOneWay(T paramT);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\Dispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */