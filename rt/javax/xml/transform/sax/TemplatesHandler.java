package javax.xml.transform.sax;

import javax.xml.transform.Templates;
import org.xml.sax.ContentHandler;

public interface TemplatesHandler extends ContentHandler {
  Templates getTemplates();
  
  void setSystemId(String paramString);
  
  String getSystemId();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\transform\sax\TemplatesHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */