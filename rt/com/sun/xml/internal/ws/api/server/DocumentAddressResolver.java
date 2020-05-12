package com.sun.xml.internal.ws.api.server;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public interface DocumentAddressResolver {
  @Nullable
  String getRelativeAddressFor(@NotNull SDDocument paramSDDocument1, @NotNull SDDocument paramSDDocument2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\server\DocumentAddressResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */