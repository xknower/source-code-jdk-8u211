package javax.lang.model.element;

import java.util.List;

public interface Parameterizable extends Element {
  List<? extends TypeParameterElement> getTypeParameters();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\element\Parameterizable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */