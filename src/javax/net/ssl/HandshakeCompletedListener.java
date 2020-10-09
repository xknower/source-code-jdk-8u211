package javax.net.ssl;

import java.util.EventListener;

public interface HandshakeCompletedListener extends EventListener {
  void handshakeCompleted(HandshakeCompletedEvent paramHandshakeCompletedEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\net\ssl\HandshakeCompletedListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */