package com.sun.org.glassfish.gmbal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameValue {}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\glassfish\gmbal\NameValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */