package javax.lang.model.element;

public interface ElementVisitor<R, P> {
  R visit(Element paramElement, P paramP);
  
  R visit(Element paramElement);
  
  R visitPackage(PackageElement paramPackageElement, P paramP);
  
  R visitType(TypeElement paramTypeElement, P paramP);
  
  R visitVariable(VariableElement paramVariableElement, P paramP);
  
  R visitExecutable(ExecutableElement paramExecutableElement, P paramP);
  
  R visitTypeParameter(TypeParameterElement paramTypeParameterElement, P paramP);
  
  R visitUnknown(Element paramElement, P paramP);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\element\ElementVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */