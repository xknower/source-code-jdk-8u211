package javax.lang.model.type;

import java.util.List;
import javax.lang.model.element.Element;

public interface DeclaredType extends ReferenceType {
  Element asElement();
  
  TypeMirror getEnclosingType();
  
  List<? extends TypeMirror> getTypeArguments();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\type\DeclaredType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */