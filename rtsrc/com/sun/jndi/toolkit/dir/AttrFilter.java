package com.sun.jndi.toolkit.dir;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public interface AttrFilter {
  boolean check(Attributes paramAttributes) throws NamingException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\toolkit\dir\AttrFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */