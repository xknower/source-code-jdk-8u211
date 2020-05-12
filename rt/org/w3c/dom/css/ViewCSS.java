package org.w3c.dom.css;

import org.w3c.dom.Element;
import org.w3c.dom.views.AbstractView;

public interface ViewCSS extends AbstractView {
  CSSStyleDeclaration getComputedStyle(Element paramElement, String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\css\ViewCSS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */