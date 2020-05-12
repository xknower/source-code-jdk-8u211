package javax.xml.ws.handler;

public interface Handler<C extends MessageContext> {
  boolean handleMessage(C paramC);
  
  boolean handleFault(C paramC);
  
  void close(MessageContext paramMessageContext);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\handler\Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */