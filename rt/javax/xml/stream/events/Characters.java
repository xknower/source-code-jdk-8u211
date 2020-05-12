package javax.xml.stream.events;

public interface Characters extends XMLEvent {
  String getData();
  
  boolean isWhiteSpace();
  
  boolean isCData();
  
  boolean isIgnorableWhiteSpace();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\stream\events\Characters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */