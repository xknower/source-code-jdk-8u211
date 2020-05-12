package javax.xml.stream.events;

import java.util.List;

public interface DTD extends XMLEvent {
  String getDocumentTypeDeclaration();
  
  Object getProcessedDTD();
  
  List getNotations();
  
  List getEntities();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\stream\events\DTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */