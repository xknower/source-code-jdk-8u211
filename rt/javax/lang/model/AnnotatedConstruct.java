package javax.lang.model;

import java.util.List;
import javax.lang.model.element.AnnotationMirror;

public interface AnnotatedConstruct {
  List<? extends AnnotationMirror> getAnnotationMirrors();
  
  <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> paramClass);
  
  <A extends java.lang.annotation.Annotation> A[] getAnnotationsByType(Class<A> paramClass);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\AnnotatedConstruct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */