package sun.reflect;

import java.lang.reflect.InvocationTargetException;

public interface ConstructorAccessor {
  Object newInstance(Object[] paramArrayOfObject) throws InstantiationException, IllegalArgumentException, InvocationTargetException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\ConstructorAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */