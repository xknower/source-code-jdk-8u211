package java.lang.instrument;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public interface ClassFileTransformer {
  byte[] transform(ClassLoader paramClassLoader, String paramString, Class<?> paramClass, ProtectionDomain paramProtectionDomain, byte[] paramArrayOfbyte) throws IllegalClassFormatException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\instrument\ClassFileTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */