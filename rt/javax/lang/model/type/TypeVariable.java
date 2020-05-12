package javax.lang.model.type;

import javax.lang.model.element.Element;

public interface TypeVariable extends ReferenceType {
  Element asElement();
  
  TypeMirror getUpperBound();
  
  TypeMirror getLowerBound();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\type\TypeVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */