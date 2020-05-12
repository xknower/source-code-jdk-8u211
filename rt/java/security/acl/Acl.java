package java.security.acl;

import java.security.Principal;
import java.util.Enumeration;

public interface Acl extends Owner {
  void setName(Principal paramPrincipal, String paramString) throws NotOwnerException;
  
  String getName();
  
  boolean addEntry(Principal paramPrincipal, AclEntry paramAclEntry) throws NotOwnerException;
  
  boolean removeEntry(Principal paramPrincipal, AclEntry paramAclEntry) throws NotOwnerException;
  
  Enumeration<Permission> getPermissions(Principal paramPrincipal);
  
  Enumeration<AclEntry> entries();
  
  boolean checkPermission(Principal paramPrincipal, Permission paramPermission);
  
  String toString();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\acl\Acl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */