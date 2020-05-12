package com.sun.security.jgss;

import jdk.Exported;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSName;

@Exported
public interface ExtendedGSSCredential extends GSSCredential {
  GSSCredential impersonate(GSSName paramGSSName) throws GSSException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\jgss\ExtendedGSSCredential.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */