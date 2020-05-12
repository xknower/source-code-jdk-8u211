package com.sun.xml.internal.ws.api.server;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.pipe.Codec;

public interface EndpointAwareCodec extends Codec {
  void setEndpoint(@NotNull WSEndpoint paramWSEndpoint);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\server\EndpointAwareCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */