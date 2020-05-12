package javax.xml.ws;

import java.util.List;
import javax.xml.ws.handler.Handler;

public interface Binding {
  List<Handler> getHandlerChain();
  
  void setHandlerChain(List<Handler> paramList);
  
  String getBindingID();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\Binding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */