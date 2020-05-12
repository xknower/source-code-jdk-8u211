package com.sun.xml.internal.ws.server.sei;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.message.Packet;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Invoker {
  public abstract Object invoke(@NotNull Packet paramPacket, @NotNull Method paramMethod, @NotNull Object... paramVarArgs) throws InvocationTargetException, IllegalAccessException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\server\sei\Invoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */