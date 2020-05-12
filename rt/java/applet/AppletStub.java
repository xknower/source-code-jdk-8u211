package java.applet;

import java.net.URL;

public interface AppletStub {
  boolean isActive();
  
  URL getDocumentBase();
  
  URL getCodeBase();
  
  String getParameter(String paramString);
  
  AppletContext getAppletContext();
  
  void appletResize(int paramInt1, int paramInt2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\applet\AppletStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */