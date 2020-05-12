package sun.reflect.annotation;

import java.io.Serializable;

public abstract class ExceptionProxy implements Serializable {
  protected abstract RuntimeException generateException();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\annotation\ExceptionProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */