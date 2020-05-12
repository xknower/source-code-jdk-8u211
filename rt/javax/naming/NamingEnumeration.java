package javax.naming;

import java.util.Enumeration;

public interface NamingEnumeration<T> extends Enumeration<T> {
  T next() throws NamingException;
  
  boolean hasMore() throws NamingException;
  
  void close() throws NamingException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\naming\NamingEnumeration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */