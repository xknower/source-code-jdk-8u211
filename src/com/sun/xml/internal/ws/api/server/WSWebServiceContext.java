package com.sun.xml.internal.ws.api.server;

import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.ws.api.message.Packet;
import javax.xml.ws.WebServiceContext;

public interface WSWebServiceContext extends WebServiceContext {
  @Nullable
  Packet getRequestPacket();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\server\WSWebServiceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */