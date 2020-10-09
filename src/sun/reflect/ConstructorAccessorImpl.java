package sun.reflect;

import java.lang.reflect.InvocationTargetException;

abstract class ConstructorAccessorImpl extends MagicAccessorImpl implements ConstructorAccessor {
  public abstract Object newInstance(Object[] paramArrayOfObject) throws InstantiationException, IllegalArgumentException, InvocationTargetException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\ConstructorAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */