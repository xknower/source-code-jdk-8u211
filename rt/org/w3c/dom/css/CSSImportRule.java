package org.w3c.dom.css;

import org.w3c.dom.stylesheets.MediaList;

public interface CSSImportRule extends CSSRule {
  String getHref();
  
  MediaList getMedia();
  
  CSSStyleSheet getStyleSheet();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\css\CSSImportRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */