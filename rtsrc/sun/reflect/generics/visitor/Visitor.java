package sun.reflect.generics.visitor;

import sun.reflect.generics.tree.ClassSignature;
import sun.reflect.generics.tree.MethodTypeSignature;

public interface Visitor<T> extends TypeTreeVisitor<T> {
  void visitClassSignature(ClassSignature paramClassSignature);
  
  void visitMethodTypeSignature(MethodTypeSignature paramMethodTypeSignature);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\generics\visitor\Visitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */