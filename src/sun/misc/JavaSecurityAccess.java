package sun.misc;

import java.security.AccessControlContext;
import java.security.PrivilegedAction;

public interface JavaSecurityAccess {
  <T> T doIntersectionPrivilege(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext1, AccessControlContext paramAccessControlContext2);
  
  <T> T doIntersectionPrivilege(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\JavaSecurityAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */