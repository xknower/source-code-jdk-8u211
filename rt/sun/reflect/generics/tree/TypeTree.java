package sun.reflect.generics.tree;

import sun.reflect.generics.visitor.TypeTreeVisitor;

public interface TypeTree extends Tree {
  void accept(TypeTreeVisitor<?> paramTypeTreeVisitor);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\generics\tree\TypeTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */