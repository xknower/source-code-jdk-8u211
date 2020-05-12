package java.security.acl;

import java.security.Principal;
import java.util.Enumeration;

public interface Group extends Principal {
  boolean addMember(Principal paramPrincipal);
  
  boolean removeMember(Principal paramPrincipal);
  
  boolean isMember(Principal paramPrincipal);
  
  Enumeration<? extends Principal> members();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\acl\Group.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */