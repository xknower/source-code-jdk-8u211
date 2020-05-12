package java.util.logging;

@FunctionalInterface
public interface Filter {
  boolean isLoggable(LogRecord paramLogRecord);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\logging\Filter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */