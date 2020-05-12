package com.sun.jmx.snmp;

public interface UserAcl {
  String getName();
  
  boolean checkReadPermission(String paramString);
  
  boolean checkReadPermission(String paramString1, String paramString2, int paramInt);
  
  boolean checkContextName(String paramString);
  
  boolean checkWritePermission(String paramString);
  
  boolean checkWritePermission(String paramString1, String paramString2, int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\UserAcl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */