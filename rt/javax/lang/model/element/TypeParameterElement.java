package javax.lang.model.element;

import java.util.List;
import javax.lang.model.type.TypeMirror;

public interface TypeParameterElement extends Element {
  Element getGenericElement();
  
  List<? extends TypeMirror> getBounds();
  
  Element getEnclosingElement();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\element\TypeParameterElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */