package javax.lang.model.element;

public interface AnnotationValue {
  Object getValue();
  
  String toString();
  
  <R, P> R accept(AnnotationValueVisitor<R, P> paramAnnotationValueVisitor, P paramP);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\element\AnnotationValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */