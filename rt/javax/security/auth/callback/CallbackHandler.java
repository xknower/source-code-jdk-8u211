package javax.security.auth.callback;

import java.io.IOException;

public interface CallbackHandler {
  void handle(Callback[] paramArrayOfCallback) throws IOException, UnsupportedCallbackException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\security\auth\callback\CallbackHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */