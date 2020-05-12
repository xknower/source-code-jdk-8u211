package org.w3c.dom.traversal;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public interface NodeIterator {
  Node getRoot();
  
  int getWhatToShow();
  
  NodeFilter getFilter();
  
  boolean getExpandEntityReferences();
  
  Node nextNode() throws DOMException;
  
  Node previousNode() throws DOMException;
  
  void detach();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\traversal\NodeIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */