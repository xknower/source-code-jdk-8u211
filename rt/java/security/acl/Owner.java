package java.security.acl;

import java.security.Principal;

public interface Owner {
  boolean addOwner(Principal paramPrincipal1, Principal paramPrincipal2) throws NotOwnerException;
  
  boolean deleteOwner(Principal paramPrincipal1, Principal paramPrincipal2) throws NotOwnerException, LastOwnerException;
  
  boolean isOwner(Principal paramPrincipal);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\acl\Owner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */