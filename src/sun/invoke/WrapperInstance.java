package sun.invoke;

import java.lang.invoke.MethodHandle;

public interface WrapperInstance {
  MethodHandle getWrapperInstanceTarget();
  
  Class<?> getWrapperInstanceType();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\invoke\WrapperInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */