package com.sun.org.apache.xml.internal.serializer;

import javax.xml.transform.Transformer;
import org.w3c.dom.Node;

public interface TransformStateSetter {
  void setCurrentNode(Node paramNode);
  
  void resetState(Transformer paramTransformer);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\serializer\TransformStateSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */