package com.sun.security.sasl.digest;

import javax.security.sasl.SaslException;

interface SecurityCtx {
  byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException;
  
  byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\sasl\digest\SecurityCtx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */