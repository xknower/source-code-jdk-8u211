package java.beans;

import java.applet.Applet;
import java.beans.beancontext.BeanContext;

public interface AppletInitializer {
  void initialize(Applet paramApplet, BeanContext paramBeanContext);
  
  void activate(Applet paramApplet);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\beans\AppletInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */