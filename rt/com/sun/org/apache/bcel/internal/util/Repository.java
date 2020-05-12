package com.sun.org.apache.bcel.internal.util;

import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import java.io.Serializable;

public interface Repository extends Serializable {
  void storeClass(JavaClass paramJavaClass);
  
  void removeClass(JavaClass paramJavaClass);
  
  JavaClass findClass(String paramString);
  
  JavaClass loadClass(String paramString) throws ClassNotFoundException;
  
  JavaClass loadClass(Class paramClass) throws ClassNotFoundException;
  
  void clear();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\interna\\util\Repository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */