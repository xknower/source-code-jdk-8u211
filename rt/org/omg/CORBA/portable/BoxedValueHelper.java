package org.omg.CORBA.portable;

import java.io.Serializable;

public interface BoxedValueHelper {
  Serializable read_value(InputStream paramInputStream);
  
  void write_value(OutputStream paramOutputStream, Serializable paramSerializable);
  
  String get_id();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\portable\BoxedValueHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */