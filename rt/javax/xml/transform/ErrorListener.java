package javax.xml.transform;

public interface ErrorListener {
  void warning(TransformerException paramTransformerException) throws TransformerException;
  
  void error(TransformerException paramTransformerException) throws TransformerException;
  
  void fatalError(TransformerException paramTransformerException) throws TransformerException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\transform\ErrorListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */