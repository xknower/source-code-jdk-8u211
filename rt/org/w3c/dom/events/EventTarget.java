package org.w3c.dom.events;

public interface EventTarget {
  void addEventListener(String paramString, EventListener paramEventListener, boolean paramBoolean);
  
  void removeEventListener(String paramString, EventListener paramEventListener, boolean paramBoolean);
  
  boolean dispatchEvent(Event paramEvent) throws EventException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\events\EventTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */