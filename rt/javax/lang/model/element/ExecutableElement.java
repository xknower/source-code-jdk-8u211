package javax.lang.model.element;

import java.util.List;
import javax.lang.model.type.TypeMirror;

public interface ExecutableElement extends Element, Parameterizable {
  List<? extends TypeParameterElement> getTypeParameters();
  
  TypeMirror getReturnType();
  
  List<? extends VariableElement> getParameters();
  
  TypeMirror getReceiverType();
  
  boolean isVarArgs();
  
  boolean isDefault();
  
  List<? extends TypeMirror> getThrownTypes();
  
  AnnotationValue getDefaultValue();
  
  Name getSimpleName();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\element\ExecutableElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */