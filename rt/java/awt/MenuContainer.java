package java.awt;

public interface MenuContainer {
  Font getFont();
  
  void remove(MenuComponent paramMenuComponent);
  
  @Deprecated
  boolean postEvent(Event paramEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\MenuContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */