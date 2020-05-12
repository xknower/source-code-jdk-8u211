package javax.xml.stream.events;

public interface StartDocument extends XMLEvent {
  String getSystemId();
  
  String getCharacterEncodingScheme();
  
  boolean encodingSet();
  
  boolean isStandalone();
  
  boolean standaloneSet();
  
  String getVersion();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\stream\events\StartDocument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */