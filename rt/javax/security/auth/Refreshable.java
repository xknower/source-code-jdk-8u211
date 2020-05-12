package javax.security.auth;

public interface Refreshable {
  boolean isCurrent();
  
  void refresh() throws RefreshFailedException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\security\auth\Refreshable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */