package org.xml.sax.ext;

import java.io.IOException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public interface EntityResolver2 extends EntityResolver {
  InputSource getExternalSubset(String paramString1, String paramString2) throws SAXException, IOException;
  
  InputSource resolveEntity(String paramString1, String paramString2, String paramString3, String paramString4) throws SAXException, IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\xml\sax\ext\EntityResolver2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */