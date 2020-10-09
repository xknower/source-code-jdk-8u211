package sun.swing;

import javax.swing.Icon;
import javax.swing.JMenuItem;

public interface MenuItemCheckIconFactory {
  Icon getIcon(JMenuItem paramJMenuItem);
  
  boolean isCompatible(Object paramObject, String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\swing\MenuItemCheckIconFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */