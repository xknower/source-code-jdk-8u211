package javax.xml.xpath;

import java.util.List;

public interface XPathFunction {
  Object evaluate(List paramList) throws XPathFunctionException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\xpath\XPathFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */