/*    */ package com.sun.xml.internal.ws.client.dispatch;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*    */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import com.sun.xml.internal.ws.api.message.Messages;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*    */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*    */ import com.sun.xml.internal.ws.encoding.xml.XMLMessage;
/*    */ import com.sun.xml.internal.ws.message.source.PayloadSourceMessage;
/*    */ import java.io.IOException;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.transform.Source;
/*    */ import javax.xml.transform.stream.StreamSource;
/*    */ import javax.xml.ws.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class RESTSourceDispatch
/*    */   extends DispatchImpl<Source>
/*    */ {
/*    */   @Deprecated
/*    */   public RESTSourceDispatch(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/* 58 */     super(port, mode, owner, pipe, binding, epr);
/* 59 */     assert isXMLHttp(binding);
/*    */   }
/*    */   
/*    */   public RESTSourceDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/* 63 */     super(portInfo, mode, binding, epr);
/* 64 */     assert isXMLHttp(binding);
/*    */   }
/*    */ 
/*    */   
/*    */   Source toReturnValue(Packet response) {
/* 69 */     Message msg = response.getMessage();
/*    */     try {
/* 71 */       return new StreamSource(XMLMessage.getDataSource(msg, this.binding.getFeatures()).getInputStream());
/* 72 */     } catch (IOException e) {
/* 73 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   Packet createPacket(Source msg) {
/*    */     Message message;
/* 81 */     if (msg == null) {
/* 82 */       message = Messages.createEmpty(this.soapVersion);
/*    */     } else {
/* 84 */       message = new PayloadSourceMessage(null, msg, setOutboundAttachments(), this.soapVersion);
/*    */     } 
/* 86 */     return new Packet(message);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\client\dispatch\RESTSourceDispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */