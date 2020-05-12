package javax.swing.event;

import java.util.EventListener;

public interface DocumentListener extends EventListener {
  void insertUpdate(DocumentEvent paramDocumentEvent);
  
  void removeUpdate(DocumentEvent paramDocumentEvent);
  
  void changedUpdate(DocumentEvent paramDocumentEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\DocumentListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */