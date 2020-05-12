package com.sun.xml.internal.ws.api;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.ws.Service;

public abstract class WSDLLocator {
  public abstract URL locateWSDL(Class<Service> paramClass, String paramString) throws MalformedURLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\WSDLLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */