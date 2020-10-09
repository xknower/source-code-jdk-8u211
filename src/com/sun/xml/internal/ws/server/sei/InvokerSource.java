package com.sun.xml.internal.ws.server.sei;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.message.Packet;

public interface InvokerSource<T extends Invoker> {
  @NotNull
  T getInvoker(Packet paramPacket);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\server\sei\InvokerSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */