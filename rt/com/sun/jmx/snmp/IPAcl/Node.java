package com.sun.jmx.snmp.IPAcl;

interface Node {
  void jjtOpen();
  
  void jjtClose();
  
  void jjtSetParent(Node paramNode);
  
  Node jjtGetParent();
  
  void jjtAddChild(Node paramNode, int paramInt);
  
  Node jjtGetChild(int paramInt);
  
  int jjtGetNumChildren();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\Node.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */