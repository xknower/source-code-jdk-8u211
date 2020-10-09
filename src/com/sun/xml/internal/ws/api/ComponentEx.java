package com.sun.xml.internal.ws.api;

import com.sun.istack.internal.NotNull;

public interface ComponentEx extends Component {
  @NotNull
  <S> Iterable<S> getIterableSPI(@NotNull Class<S> paramClass);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\ComponentEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */