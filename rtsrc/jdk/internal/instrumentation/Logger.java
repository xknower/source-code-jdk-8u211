package jdk.internal.instrumentation;

public interface Logger {
  void error(String paramString);
  
  void warn(String paramString);
  
  void info(String paramString);
  
  void debug(String paramString);
  
  void trace(String paramString);
  
  void error(String paramString, Throwable paramThrowable);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\instrumentation\Logger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */