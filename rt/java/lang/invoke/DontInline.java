package java.lang.invoke;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@interface DontInline {}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\invoke\DontInline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */