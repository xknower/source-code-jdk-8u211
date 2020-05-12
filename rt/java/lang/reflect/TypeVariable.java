package java.lang.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

public interface TypeVariable<D extends GenericDeclaration> extends Type, AnnotatedElement {
  Type[] getBounds();
  
  D getGenericDeclaration();
  
  String getName();
  
  AnnotatedType[] getAnnotatedBounds();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\reflect\TypeVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */