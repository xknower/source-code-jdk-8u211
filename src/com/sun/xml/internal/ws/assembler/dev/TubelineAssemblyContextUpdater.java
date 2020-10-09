package com.sun.xml.internal.ws.assembler.dev;

import javax.xml.ws.WebServiceException;

public interface TubelineAssemblyContextUpdater {
  void prepareContext(ClientTubelineAssemblyContext paramClientTubelineAssemblyContext) throws WebServiceException;
  
  void prepareContext(ServerTubelineAssemblyContext paramServerTubelineAssemblyContext) throws WebServiceException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\assembler\dev\TubelineAssemblyContextUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */