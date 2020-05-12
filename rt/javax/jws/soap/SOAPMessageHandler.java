package javax.jws.soap;

@Deprecated
public @interface SOAPMessageHandler {
  String name() default "";
  
  String className();
  
  InitParam[] initParams() default {};
  
  String[] roles() default {};
  
  String[] headers() default {};
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\jws\soap\SOAPMessageHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */