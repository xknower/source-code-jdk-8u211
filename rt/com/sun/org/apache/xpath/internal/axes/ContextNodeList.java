package com.sun.org.apache.xpath.internal.axes;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public interface ContextNodeList {
  Node getCurrentNode();
  
  int getCurrentPos();
  
  void reset();
  
  void setShouldCacheNodes(boolean paramBoolean);
  
  void runTo(int paramInt);
  
  void setCurrentPos(int paramInt);
  
  int size();
  
  boolean isFresh();
  
  NodeIterator cloneWithReset() throws CloneNotSupportedException;
  
  Object clone() throws CloneNotSupportedException;
  
  int getLast();
  
  void setLast(int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\axes\ContextNodeList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */