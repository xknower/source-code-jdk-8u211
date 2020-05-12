package javax.xml.soap;

public interface SOAPHeaderElement extends SOAPElement {
  void setActor(String paramString);
  
  void setRole(String paramString) throws SOAPException;
  
  String getActor();
  
  String getRole();
  
  void setMustUnderstand(boolean paramBoolean);
  
  boolean getMustUnderstand();
  
  void setRelay(boolean paramBoolean) throws SOAPException;
  
  boolean getRelay();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\soap\SOAPHeaderElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */