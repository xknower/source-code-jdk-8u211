package javax.management;

import java.io.Serializable;

public interface QueryExp extends Serializable {
  boolean apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException;
  
  void setMBeanServer(MBeanServer paramMBeanServer);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\QueryExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */