package javax.xml.ws.spi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.xml.ws.WebServiceContext;

public abstract class Invoker {
  public abstract void inject(WebServiceContext paramWebServiceContext) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
  
  public abstract Object invoke(Method paramMethod, Object... paramVarArgs) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\spi\Invoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */