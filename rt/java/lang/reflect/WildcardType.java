package java.lang.reflect;

import java.lang.reflect.Type;

public interface WildcardType extends Type {
  Type[] getUpperBounds();
  
  Type[] getLowerBounds();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\reflect\WildcardType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */