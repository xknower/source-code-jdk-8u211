package org.w3c.dom.traversal;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public interface TreeWalker {
  Node getRoot();
  
  int getWhatToShow();
  
  NodeFilter getFilter();
  
  boolean getExpandEntityReferences();
  
  Node getCurrentNode();
  
  void setCurrentNode(Node paramNode) throws DOMException;
  
  Node parentNode();
  
  Node firstChild();
  
  Node lastChild();
  
  Node previousSibling();
  
  Node nextSibling();
  
  Node previousNode();
  
  Node nextNode();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\traversal\TreeWalker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */