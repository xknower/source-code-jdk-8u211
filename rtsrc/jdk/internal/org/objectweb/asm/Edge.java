package jdk.internal.org.objectweb.asm;

class Edge {
  static final int NORMAL = 0;
  
  static final int EXCEPTION = 2147483647;
  
  int info;
  
  Label successor;
  
  Edge next;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\org\objectweb\asm\Edge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */