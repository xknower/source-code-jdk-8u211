package javax.xml.ws;

import java.util.Map;
import java.util.concurrent.Future;

public interface Response<T> extends Future<T> {
  Map<String, Object> getContext();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\Response.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */