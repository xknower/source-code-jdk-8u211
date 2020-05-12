package sun.misc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Contended {
  String value() default "";
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\Contended.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */