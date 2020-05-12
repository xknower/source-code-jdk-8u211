package sun.misc;

import java.net.HttpCookie;
import java.util.List;

public interface JavaNetHttpCookieAccess {
  List<HttpCookie> parse(String paramString);
  
  String header(HttpCookie paramHttpCookie);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\JavaNetHttpCookieAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */