package java.lang.invoke;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface Stable {}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\invoke\Stable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */