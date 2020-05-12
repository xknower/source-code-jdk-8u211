package javax.xml.validation;

import org.w3c.dom.TypeInfo;

public abstract class TypeInfoProvider {
  public abstract TypeInfo getElementTypeInfo();
  
  public abstract TypeInfo getAttributeTypeInfo(int paramInt);
  
  public abstract boolean isIdAttribute(int paramInt);
  
  public abstract boolean isSpecified(int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\validation\TypeInfoProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */