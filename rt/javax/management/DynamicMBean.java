package javax.management;

public interface DynamicMBean {
  Object getAttribute(String paramString) throws AttributeNotFoundException, MBeanException, ReflectionException;
  
  void setAttribute(Attribute paramAttribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException;
  
  AttributeList getAttributes(String[] paramArrayOfString);
  
  AttributeList setAttributes(AttributeList paramAttributeList);
  
  Object invoke(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws MBeanException, ReflectionException;
  
  MBeanInfo getMBeanInfo();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\DynamicMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */