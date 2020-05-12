package javax.xml.bind.annotation;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Source;

public interface DomHandler<ElementT, ResultT extends javax.xml.transform.Result> {
  ResultT createUnmarshaller(ValidationEventHandler paramValidationEventHandler);
  
  ElementT getElement(ResultT paramResultT);
  
  Source marshal(ElementT paramElementT, ValidationEventHandler paramValidationEventHandler);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\bind\annotation\DomHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */