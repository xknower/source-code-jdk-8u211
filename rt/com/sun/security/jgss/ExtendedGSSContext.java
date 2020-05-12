package com.sun.security.jgss;

import jdk.Exported;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;

@Exported
public interface ExtendedGSSContext extends GSSContext {
  Object inquireSecContext(InquireType paramInquireType) throws GSSException;
  
  void requestDelegPolicy(boolean paramBoolean) throws GSSException;
  
  boolean getDelegPolicyState();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\jgss\ExtendedGSSContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */