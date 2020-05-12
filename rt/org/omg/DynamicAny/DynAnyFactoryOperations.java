package org.omg.DynamicAny;

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;

public interface DynAnyFactoryOperations {
  DynAny create_dyn_any(Any paramAny) throws InconsistentTypeCode;
  
  DynAny create_dyn_any_from_type_code(TypeCode paramTypeCode) throws InconsistentTypeCode;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\DynamicAny\DynAnyFactoryOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */