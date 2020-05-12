package com.sun.xml.internal.ws.api.server;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.ws.api.message.Packet;
import java.security.Principal;

public interface WebServiceContextDelegate {
  Principal getUserPrincipal(@NotNull Packet paramPacket);
  
  boolean isUserInRole(@NotNull Packet paramPacket, String paramString);
  
  @NotNull
  String getEPRAddress(@NotNull Packet paramPacket, @NotNull WSEndpoint paramWSEndpoint);
  
  @Nullable
  String getWSDLAddress(@NotNull Packet paramPacket, @NotNull WSEndpoint paramWSEndpoint);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\server\WebServiceContextDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */