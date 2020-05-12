package sun.security.krb5.internal;

public interface SeqNumber {
  void randInit();
  
  void init(int paramInt);
  
  int current();
  
  int next();
  
  int step();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\SeqNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */