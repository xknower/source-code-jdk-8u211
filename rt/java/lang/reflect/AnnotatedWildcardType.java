package java.lang.reflect;

import java.lang.reflect.AnnotatedType;

public interface AnnotatedWildcardType extends AnnotatedType {
  AnnotatedType[] getAnnotatedLowerBounds();
  
  AnnotatedType[] getAnnotatedUpperBounds();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\reflect\AnnotatedWildcardType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */