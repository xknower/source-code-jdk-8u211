package javax.xml.ws.handler.soap;

import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;

public interface SOAPHandler<T extends SOAPMessageContext> extends Handler<T> {
  Set<QName> getHeaders();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\handler\soap\SOAPHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */