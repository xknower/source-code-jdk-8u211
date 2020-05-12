package javax.xml.transform;

public interface SourceLocator {
  String getPublicId();
  
  String getSystemId();
  
  int getLineNumber();
  
  int getColumnNumber();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\transform\SourceLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */