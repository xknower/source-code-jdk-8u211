package javax.print;

import javax.print.attribute.Attribute;

public interface AttributeException {
  Class[] getUnsupportedAttributes();
  
  Attribute[] getUnsupportedValues();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\print\AttributeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */