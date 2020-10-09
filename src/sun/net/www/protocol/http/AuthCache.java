package sun.net.www.protocol.http;

public interface AuthCache {
  void put(String paramString, AuthCacheValue paramAuthCacheValue);
  
  AuthCacheValue get(String paramString1, String paramString2);
  
  void remove(String paramString, AuthCacheValue paramAuthCacheValue);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\www\protocol\http\AuthCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */