package com.sun.corba.se.impl.orbutil.graph;

import java.util.Set;

public interface Graph extends Set {
  NodeData getNodeData(Node paramNode);
  
  Set getRoots();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\orbutil\graph\Graph.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */