package sun.misc;

import java.io.FileDescriptor;

public interface JavaIOFileDescriptorAccess {
  void set(FileDescriptor paramFileDescriptor, int paramInt);
  
  int get(FileDescriptor paramFileDescriptor);
  
  void setHandle(FileDescriptor paramFileDescriptor, long paramLong);
  
  long getHandle(FileDescriptor paramFileDescriptor);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\JavaIOFileDescriptorAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */