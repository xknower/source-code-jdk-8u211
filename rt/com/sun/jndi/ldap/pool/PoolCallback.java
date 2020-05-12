package com.sun.jndi.ldap.pool;

public interface PoolCallback {
  boolean releasePooledConnection(PooledConnection paramPooledConnection);
  
  boolean removePooledConnection(PooledConnection paramPooledConnection);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\ldap\pool\PoolCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */