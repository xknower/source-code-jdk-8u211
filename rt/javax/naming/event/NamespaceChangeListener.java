package javax.naming.event;

public interface NamespaceChangeListener extends NamingListener {
  void objectAdded(NamingEvent paramNamingEvent);
  
  void objectRemoved(NamingEvent paramNamingEvent);
  
  void objectRenamed(NamingEvent paramNamingEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\naming\event\NamespaceChangeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */