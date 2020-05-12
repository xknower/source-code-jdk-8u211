package java.lang.reflect;

import java.lang.reflect.Type;

public interface ParameterizedType extends Type {
  Type[] getActualTypeArguments();
  
  Type getRawType();
  
  Type getOwnerType();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\reflect\ParameterizedType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */