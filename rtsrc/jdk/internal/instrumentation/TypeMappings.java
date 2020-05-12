package jdk.internal.instrumentation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TypeMappings {
  TypeMapping[] value();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\instrumentation\TypeMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */