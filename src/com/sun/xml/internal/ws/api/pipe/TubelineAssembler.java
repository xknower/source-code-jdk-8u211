package com.sun.xml.internal.ws.api.pipe;

import com.sun.istack.internal.NotNull;

public interface TubelineAssembler {
  @NotNull
  Tube createClient(@NotNull ClientTubeAssemblerContext paramClientTubeAssemblerContext);
  
  @NotNull
  Tube createServer(@NotNull ServerTubeAssemblerContext paramServerTubeAssemblerContext);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\pipe\TubelineAssembler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */