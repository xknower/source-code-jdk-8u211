package org.omg.CORBA.portable;

import org.omg.CORBA.TypeCode;

public interface Streamable {
  void _read(InputStream paramInputStream);
  
  void _write(OutputStream paramOutputStream);
  
  TypeCode _type();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\portable\Streamable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */