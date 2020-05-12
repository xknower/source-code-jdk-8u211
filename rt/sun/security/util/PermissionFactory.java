package sun.security.util;

public interface PermissionFactory<T extends java.security.Permission> {
  T newPermission(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\securit\\util\PermissionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */