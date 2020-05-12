package sun.misc;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public interface JavaNioAccess {
  BufferPool getDirectBufferPool();
  
  ByteBuffer newDirectByteBuffer(long paramLong, int paramInt, Object paramObject);
  
  void truncate(Buffer paramBuffer);
  
  public static interface BufferPool {
    String getName();
    
    long getCount();
    
    long getTotalCapacity();
    
    long getMemoryUsed();
  }
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\JavaNioAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */