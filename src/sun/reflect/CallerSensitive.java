package sun.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CallerSensitive {}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\CallerSensitive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */