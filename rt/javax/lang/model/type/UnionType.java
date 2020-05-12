package javax.lang.model.type;

import java.util.List;

public interface UnionType extends TypeMirror {
  List<? extends TypeMirror> getAlternatives();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\type\UnionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */