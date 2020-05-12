package javax.rmi.CORBA;

import java.io.Serializable;
import org.omg.CORBA.portable.OutputStream;

public interface ValueHandlerMultiFormat extends ValueHandler {
  byte getMaximumStreamFormatVersion();
  
  void writeValue(OutputStream paramOutputStream, Serializable paramSerializable, byte paramByte);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\rmi\CORBA\ValueHandlerMultiFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */