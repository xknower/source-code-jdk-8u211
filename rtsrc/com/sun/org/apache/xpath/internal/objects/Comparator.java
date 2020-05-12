package com.sun.org.apache.xpath.internal.objects;

import com.sun.org.apache.xml.internal.utils.XMLString;

abstract class Comparator {
  abstract boolean compareStrings(XMLString paramXMLString1, XMLString paramXMLString2);
  
  abstract boolean compareNumbers(double paramDouble1, double paramDouble2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\objects\Comparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */