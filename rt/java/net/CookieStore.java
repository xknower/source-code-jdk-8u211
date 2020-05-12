package java.net;

import java.util.List;

public interface CookieStore {
  void add(URI paramURI, HttpCookie paramHttpCookie);
  
  List<HttpCookie> get(URI paramURI);
  
  List<HttpCookie> getCookies();
  
  List<URI> getURIs();
  
  boolean remove(URI paramURI, HttpCookie paramHttpCookie);
  
  boolean removeAll();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\CookieStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */