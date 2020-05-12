package org.w3c.dom;

public interface DOMImplementation {
  boolean hasFeature(String paramString1, String paramString2);
  
  DocumentType createDocumentType(String paramString1, String paramString2, String paramString3) throws DOMException;
  
  Document createDocument(String paramString1, String paramString2, DocumentType paramDocumentType) throws DOMException;
  
  Object getFeature(String paramString1, String paramString2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\DOMImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */