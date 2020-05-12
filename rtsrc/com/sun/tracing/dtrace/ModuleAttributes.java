package com.sun.tracing.dtrace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ModuleAttributes {
  Attributes value();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\tracing\dtrace\ModuleAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */