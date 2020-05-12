package org.w3c.dom.ls;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface LSParserFilter {
  public static final short FILTER_ACCEPT = 1;
  
  public static final short FILTER_REJECT = 2;
  
  public static final short FILTER_SKIP = 3;
  
  public static final short FILTER_INTERRUPT = 4;
  
  short startElement(Element paramElement);
  
  short acceptNode(Node paramNode);
  
  int getWhatToShow();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\ls\LSParserFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */