package sun.security.krb5.internal;

import java.util.BitSet;
import sun.security.krb5.EncryptionKey;

public class AuthContext {
  public HostAddress remoteAddress;
  
  public int remotePort;
  
  public HostAddress localAddress;
  
  public int localPort;
  
  public EncryptionKey keyBlock;
  
  public EncryptionKey localSubkey;
  
  public EncryptionKey remoteSubkey;
  
  public BitSet authContextFlags;
  
  public int remoteSeqNumber;
  
  public int localSeqNumber;
  
  public Authenticator authenticator;
  
  public int reqCksumType;
  
  public int safeCksumType;
  
  public byte[] initializationVector;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\AuthContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */