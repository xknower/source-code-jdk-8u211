package sun.misc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.security.AccessControlContext;
import java.util.Map;
import sun.nio.ch.Interruptible;
import sun.reflect.ConstantPool;
import sun.reflect.annotation.AnnotationType;

public interface JavaLangAccess {
  ConstantPool getConstantPool(Class<?> paramClass);
  
  boolean casAnnotationType(Class<?> paramClass, AnnotationType paramAnnotationType1, AnnotationType paramAnnotationType2);
  
  AnnotationType getAnnotationType(Class<?> paramClass);
  
  Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap(Class<?> paramClass);
  
  byte[] getRawClassAnnotations(Class<?> paramClass);
  
  byte[] getRawClassTypeAnnotations(Class<?> paramClass);
  
  byte[] getRawExecutableTypeAnnotations(Executable paramExecutable);
  
  <E extends Enum<E>> E[] getEnumConstantsShared(Class<E> paramClass);
  
  void blockedOn(Thread paramThread, Interruptible paramInterruptible);
  
  void registerShutdownHook(int paramInt, boolean paramBoolean, Runnable paramRunnable);
  
  int getStackTraceDepth(Throwable paramThrowable);
  
  StackTraceElement getStackTraceElement(Throwable paramThrowable, int paramInt);
  
  String newStringUnsafe(char[] paramArrayOfchar);
  
  Thread newThreadWithAcc(Runnable paramRunnable, AccessControlContext paramAccessControlContext);
  
  void invokeFinalize(Object paramObject) throws Throwable;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\JavaLangAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */