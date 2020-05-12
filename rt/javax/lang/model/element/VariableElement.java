package javax.lang.model.element;

public interface VariableElement extends Element {
  Object getConstantValue();
  
  Name getSimpleName();
  
  Element getEnclosingElement();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\element\VariableElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */