package org.w3c.dom.css;

import org.w3c.dom.DOMException;

public interface CSSPageRule extends CSSRule {
  String getSelectorText();
  
  void setSelectorText(String paramString) throws DOMException;
  
  CSSStyleDeclaration getStyle();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\css\CSSPageRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */