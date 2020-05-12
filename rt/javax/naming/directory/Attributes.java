package javax.naming.directory;

import java.io.Serializable;
import javax.naming.NamingEnumeration;

public interface Attributes extends Cloneable, Serializable {
  boolean isCaseIgnored();
  
  int size();
  
  Attribute get(String paramString);
  
  NamingEnumeration<? extends Attribute> getAll();
  
  NamingEnumeration<String> getIDs();
  
  Attribute put(String paramString, Object paramObject);
  
  Attribute put(Attribute paramAttribute);
  
  Attribute remove(String paramString);
  
  Object clone();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\naming\directory\Attributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */