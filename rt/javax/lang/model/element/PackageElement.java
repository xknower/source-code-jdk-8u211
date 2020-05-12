package javax.lang.model.element;

import java.util.List;

public interface PackageElement extends Element, QualifiedNameable {
  Name getQualifiedName();
  
  Name getSimpleName();
  
  List<? extends Element> getEnclosedElements();
  
  boolean isUnnamed();
  
  Element getEnclosingElement();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\element\PackageElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */