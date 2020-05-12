package com.sun.jndi.ldap.pool;

import javax.naming.NamingException;

public interface PooledConnectionFactory {
  PooledConnection createPooledConnection(PoolCallback paramPoolCallback) throws NamingException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\ldap\pool\PooledConnectionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */