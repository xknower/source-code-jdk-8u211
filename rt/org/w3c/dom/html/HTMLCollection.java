package org.w3c.dom.html;

import org.w3c.dom.Node;

public interface HTMLCollection {
  int getLength();
  
  Node item(int paramInt);
  
  Node namedItem(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\html\HTMLCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */