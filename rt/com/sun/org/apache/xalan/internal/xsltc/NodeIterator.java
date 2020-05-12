package com.sun.org.apache.xalan.internal.xsltc;

public interface NodeIterator extends Cloneable {
  public static final int END = -1;
  
  int next();
  
  NodeIterator reset();
  
  int getLast();
  
  int getPosition();
  
  void setMark();
  
  void gotoMark();
  
  NodeIterator setStartNode(int paramInt);
  
  boolean isReverse();
  
  NodeIterator cloneIterator();
  
  void setRestartable(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\NodeIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */