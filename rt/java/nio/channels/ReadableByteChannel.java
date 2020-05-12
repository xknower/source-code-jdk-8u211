package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface ReadableByteChannel extends Channel {
  int read(ByteBuffer paramByteBuffer) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\channels\ReadableByteChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */