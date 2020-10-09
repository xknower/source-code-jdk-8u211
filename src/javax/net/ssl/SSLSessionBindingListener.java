package javax.net.ssl;

import java.util.EventListener;

public interface SSLSessionBindingListener extends EventListener {
  void valueBound(SSLSessionBindingEvent paramSSLSessionBindingEvent);
  
  void valueUnbound(SSLSessionBindingEvent paramSSLSessionBindingEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\net\ssl\SSLSessionBindingListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */