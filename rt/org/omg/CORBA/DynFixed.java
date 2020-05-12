package org.omg.CORBA;

import org.omg.CORBA.DynAnyPackage.InvalidValue;

@Deprecated
public interface DynFixed extends Object, DynAny {
  byte[] get_value();
  
  void set_value(byte[] paramArrayOfbyte) throws InvalidValue;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\DynFixed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */