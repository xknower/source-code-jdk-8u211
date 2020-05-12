package com.sun.org.glassfish.external.probe.provider.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Probe {
  String name() default "";
  
  boolean hidden() default false;
  
  boolean self() default false;
  
  String providerName() default "";
  
  String moduleName() default "";
  
  boolean stateful() default false;
  
  String profileNames() default "";
  
  boolean statefulReturn() default false;
  
  boolean statefulException() default false;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\glassfish\external\probe\provider\annotations\Probe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */